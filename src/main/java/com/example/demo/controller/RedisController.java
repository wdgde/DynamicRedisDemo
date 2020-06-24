package com.example.demo.controller;

import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: DynamicRedisDemo
 * @description:
 * @author: wdgde
 * @create: 2020-06-15 17:29
 **/
@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/redis")
    public void doSth() {
        //redisService.setRedis();
        //redisService.getRedis();
        redisService.setNewRedis();
        redisService.getNewRedis();
    }
}