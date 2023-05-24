package com.example.redisearch.repo;

import com.example.redisearch.model.FrogUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FrogUserRepository extends JpaRepository<FrogUserEntity, Long> {
    @Query(value = "SELECT id, frog_id, user_name, e_name FROM user_info", nativeQuery = true)
    List<FrogUserEntity> getData();

    @Query(value = "SELECT id, frog_id, user_name, e_name, now() FROM user_info WHERE user_name LIKE :username%", nativeQuery = true)
    List<FrogUserEntity> getUsersByUsername(@Param("username") String username);
}
