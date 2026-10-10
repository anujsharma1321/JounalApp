package com.firstProgram.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T get(String key, Class<T> entity){
        try {
            Object value= redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper=new ObjectMapper();
            if(value==null)
                    return null;
                return objectMapper.readValue(value.toString(),entity);
        }
        catch (Exception e) {
            log.error("Exception error: "+ e);
            return null;
        }
    }
    public void set(String key,Object value,Long ttl){
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue=objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,jsonValue,ttl,TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception error: "+ e);
        }
    }
}
