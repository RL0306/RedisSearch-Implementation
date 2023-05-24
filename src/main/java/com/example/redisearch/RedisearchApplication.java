package com.example.redisearch;

import com.example.redisearch.repo.FrogUserRepository;
import com.example.redisearch.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.FieldName;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;

@SpringBootApplication
@Slf4j
public class RedisearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisearchApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FrogUserRepository frogUserRepository;

    @Autowired
    private UnifiedJedis jedis;

    @Value("classpath:data.json")
    Resource resourceFile;

    @Bean
    CommandLineRunner loadData() {
        return args -> {

            userRepository.deleteAll();
            try {
                jedis.ftDropIndex("user-idx");
            } catch (Exception e) {
                System.out.println("Index not available");
            }

            log.info("loading in data");
            frogUserRepository.getData()
                    .forEach(e -> {
                        userRepository.saveData(e);
                    });
            log.info("done loading");


            Schema schema = new Schema()
                    .addField(new Schema.Field(FieldName.of("$.username").as("username"), Schema.FieldType.TEXT, true, false))
                    .addField(new Schema.Field(FieldName.of("$.ename").as("ename"), Schema.FieldType.TEXT))
                    .addField(new Schema.Field(FieldName.of("$.id").as("id"), Schema.FieldType.NUMERIC));

            IndexDefinition indexDefinition =
                    new IndexDefinition(IndexDefinition.Type.JSON)
                            .setPrefixes(new String[]{"user:"});


            jedis.ftCreate("user-idx", IndexOptions.defaultOptions().setDefinition(indexDefinition), schema);
        };
    }


}
