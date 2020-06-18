package com.example.demo.service;

import com.example.demo.config.RedisClusterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: DynamicRedisDemo
 * @description:
 * @author: wdgde
 * @create: 2020-06-15 17:29
 **/
@Service
public class RedisService {

//    @Autowired
//    private RedisConfig redisConfig;

    @Autowired
    private RedisClusterConfig redisClusterConfig;

    public void setRedis() {
        //Map<String, StringRedisTemplate> redisTemplateMap = redisConfig.redisTemplate();
        Map<String, StringRedisTemplate> redisTemplateMap = redisClusterConfig.redisTemplate();
        for (int i = 0; i < redisTemplateMap.size(); i++) {
            StringRedisTemplate stringRedisTemplate = redisTemplateMap.get(i + "");
            //stringRedisTemplate.opsForValue().set("redisKey1", "测试" + i);
            //例子key, field, value
            stringRedisTemplate.opsForHash().put("hash", "hash-key", "hash-value");
        }
    }

    public void getRedis() {
        //Map<String, StringRedisTemplate> redisTemplateMap = redisConfig.redisTemplate();
        Map<String, StringRedisTemplate> redisTemplateMap = redisClusterConfig.redisTemplate();
        StringRedisTemplate stringRedisTemplate = redisTemplateMap.get("1");
        System.out.println("---------------" + stringRedisTemplate.opsForHash().get("hash", "hash-key"));
    }
}