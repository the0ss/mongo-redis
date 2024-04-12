package com.theos.mongoredis.controllers;

import java.util.*;

import com.theos.mongoredis.services.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theos.mongoredis.model.User;
import com.theos.mongoredis.repository.UserRepo;
import com.theos.mongoredis.services.RedisService;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MongoService mongoService;

    @GetMapping("/id")
    public ResponseEntity<User> getUser(@RequestParam String id){
        User cachedUser= redisService.get("cachedUser", User.class);
        if(cachedUser!=null)
            return ResponseEntity.ok(cachedUser);
        System.out.println("Inside");
        Optional<User> user = userRepo.findById(id);
        user.ifPresent(value -> redisService.set("cachedUser", value, 300L));
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUser(){
        List<User> cachedUser= redisService.getAll("cachedUsers");
        if(cachedUser!=null&&!cachedUser.isEmpty())
            return ResponseEntity.ok(cachedUser);
        System.out.println("InsideAll");
        List<User> users = userRepo.findAll();
        if(!users.isEmpty()){
            redisService.setAll("cachedUsers", users, 300L);
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser= userRepo.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/mongo")
    public List<User> useMongo(){
        return mongoService.getUserById("6616b2661b09162ea07a1f66");
    }
}
