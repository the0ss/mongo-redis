package com.theos.mongoredis.services;

import java.util.List;
import java.util.concurrent.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.theos.mongoredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {
    
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedisTemplate<String,List<User>> redisAllTemplate;

    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o=redisTemplate.opsForValue().get(key);
            if(o==null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        }catch(Exception e){
            log.error("Exception", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl){
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue,ttl,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    public List<User> getAll(String key){
        try{
            Object o=redisTemplate.opsForValue().get(key);
            if(o==null)
                return null;
            ObjectMapper mapper=new ObjectMapper();
            return mapper.readValue(o.toString(), new TypeReference<List<User>>() {});
        }catch (Exception e){
            log.error("Exception",e);
            return null;
        }
    }

    public void setAll(String key, List<User> o, Long ttl){
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue,ttl,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

}
