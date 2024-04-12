package com.theos.mongoredis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.theos.mongoredis.model.User;

@Repository
public interface UserRepo extends MongoRepository<User,String> {
    
}
