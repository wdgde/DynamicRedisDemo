/*
package com.example.demo.config;

import com.example.demo.bean.RedisBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @program: DynamicRedisDemo
 * @description:
 * @author: wdgde
 * @create: 2020-06-15 16:53
 **//*

@Configuration
@ConfigurationProperties(prefix = "spring.my")
public class RedisConfig {
    private List<RedisBean> redisBean = new ArrayList<>();

    public List<RedisBean> getRedisBean() {
        return redisBean;
    }

    public void setRedisBean(List<RedisBean> redisBean) {
        this.redisBean = redisBean;
    }

    @ConfigurationProperties(prefix = "spring.redis.poolconfig")
    @Bean("jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    public RedisConnectionFactory redisConnectionFactory(RedisBean redisBean) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisBean.getHost());
        redisStandaloneConfiguration.setPort(redisBean.getPort());
        redisStandaloneConfiguration.setDatabase(redisBean.getDatabase());
        JedisClientConfiguration poolConfig = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig()).build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, poolConfig);
        return jedisConnectionFactory;
    }

    public Map<String, StringRedisTemplate> redisTemplate() {
        List<RedisBean> redisBeans = getRedisBean();
        Map<String, StringRedisTemplate> redisTemplateMap = new HashMap<>();
        for (int i = 0; i < redisBeans.size(); i++) {
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(redisConnectionFactory(redisBeans.get(i)));
            stringRedisTemplate.afterPropertiesSet();
            redisTemplateMap.put(i + "", stringRedisTemplate);
        }
        return redisTemplateMap;
    }

    //public LettuceClientConfiguration
}

*/
