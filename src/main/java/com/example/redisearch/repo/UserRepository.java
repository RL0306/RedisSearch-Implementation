package com.example.redisearch.repo;

import com.example.redisearch.model.FrogUserEntity;
import com.example.redisearch.model.FrogUserDocument;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.SearchResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    @Autowired
    private UnifiedJedis jedis;

    public FrogUserEntity saveData(FrogUserEntity u){
        Gson gson = new Gson();

        String key = "user:" + u.getId();


        jedis.jsonSet(key, gson.toJson(u));
        jedis.sadd("user", key);

        return u;
    }


    public void deleteAll() {
        Set<String> keys = jedis.smembers("user");
        if(!keys.isEmpty()){
            keys
                    .forEach(jedis::jsonDel);
        }

        jedis.del("user");
    }

    public List<FrogUserDocument> search(String username) {

        StringBuilder queryBuilder = new StringBuilder();

        if(username != null && username.length() != 0){
            queryBuilder.append("@username:").append(username).append("*");
        }

        String queryCriteria = queryBuilder.toString();
        Query query = new Query(queryCriteria);

        query.limit(0, 15);
        SearchResult searchResult = jedis.ftSearch("user-idx", query);

        return searchResult.getDocuments()
                .stream()
                .map(this::convertDocumentToPost)
                .collect(Collectors.toList());

    }



    private FrogUserDocument convertDocumentToPost(Document document){
        Gson gson = new Gson();
        String jsonDoc = document.getProperties()
                .iterator()
                .next()
                .getValue()
                .toString();

        return gson.fromJson(jsonDoc, FrogUserDocument.class);
    }
}
