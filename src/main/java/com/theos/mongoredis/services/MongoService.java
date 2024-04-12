package com.theos.mongoredis.services;

import com.theos.mongoredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserById(String key){
        Query query=new Query();
        query.addCriteria(Criteria.where("id").is(key));
        System.out.println("Mongo Service");
        return mongoTemplate.find(query,User.class);
    }
}
