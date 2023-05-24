package com.example.redisearch.service;

import com.example.redisearch.model.FrogUserDocument;
import com.example.redisearch.model.FrogUserEntity;
import com.example.redisearch.repo.FrogUserRepository;
import com.example.redisearch.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final FrogUserRepository frogUserRepository;

    public List<FrogUserDocument> search(String username) {
        return userRepository.search(username);
    }

    public List<FrogUserEntity> existingSearch(String username) {
        return frogUserRepository.getUsersByUsername(username);
    }
}
