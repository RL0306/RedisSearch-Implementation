package com.example.redisearch.controller;

import com.example.redisearch.model.FrogUserDocument;
import com.example.redisearch.model.FrogUserEntity;
import com.example.redisearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")

public class SearchController {
    @Autowired
    private UserService userService;


    @GetMapping
    public List<FrogUserDocument> search(@RequestParam(name = "username") String username){
        return userService.search(username);
    }

    @GetMapping(path = "/existing")
    public List<FrogUserEntity> existingSearch(@RequestParam(name = "username") String username){
        return userService.existingSearch(username);
    }






}
