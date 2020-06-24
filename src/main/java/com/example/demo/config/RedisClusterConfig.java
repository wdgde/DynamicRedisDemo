/*
package com.example.demo.config;

import com.example.demo.bean.RedisClusterBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.*;

*/
/**
 * @program: DynamicRedisDemo
 * @description:
 * @author: wdgde
 * @create: 2020-06-17 14:47
 **//*

@Configuration
@ConfigurationProperties(prefix = "spring.my.cluster")
public class RedisClusterConfig {
    private List<RedisClusterBean> redisClusterBean = new ArrayList<>();

    public List<RedisClusterBean> getRedisClusterBean() {
        return redisClusterBean;
    }

    public void setRedisClusterBean(List<RedisClusterBean> redisClusterBean) {
        this.redisClusterBean = redisClusterBean;
    }

    @ConfigurationProperties(prefix = "spring.cluster.redis.poolconfig")
    @Bean("jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        System.out.println("----------------------------jedispool初始化完成");
        return new JedisPoolConfig();
    }

    public RedisSentinelConfiguration redisSentinelConfiguration(RedisClusterBean redisClusterBean) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        RedisSocketConfiguration redisSocketConfiguration = new RedisSocketConfiguration();
        RedisAutoConfiguration redisAutoConfiguration = new RedisAutoConfiguration();


        String[] sub = redisClusterBean.getNodes().split(",");
        Set<RedisNode> nodeList = new HashSet<>(sub.length);
        String[] tmp;
        for (String s : sub) {
            tmp = s.split(":");
            nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
        }
        redisSentinelConfiguration.setSentinels(nodeList);
        redisSentinelConfiguration.setMaster(redisClusterBean.getMaster());
        return redisSentinelConfiguration;
    }


    public RedisConnectionFactory redisConnectionFactory(RedisClusterBean redisClusterBean) {
        //Jedis方式
        //JedisClientConfiguration的方式不能jedisConnectionFactory.setUsePool(true)
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig()).build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration(redisClusterBean), jedisPoolConfig());
        jedisConnectionFactory.setHostName(redisClusterBean.getHost());
        jedisConnectionFactory.setPort(redisClusterBean.getPort());
        jedisConnectionFactory.setDatabase(redisClusterBean.getDatabase());
        jedisConnectionFactory.setUsePool(true);
        System.out.println("-------------" + jedisConnectionFactory.getPoolConfig());
        //Lettuce方式
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(1234)).poolConfig(jedisPoolConfig()).build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration(redisClusterBean), lettuceClientConfiguration);
        lettuceConnectionFactory.setDatabase(redisClusterBean.getDatabase());
        lettuceConnectionFactory.setHostName(redisClusterBean.getHost());
        lettuceConnectionFactory.setPort(redisClusterBean.getPort());
        lettuceConnectionFactory.afterPropertiesSet();

        //返回
        return jedisConnectionFactory;
        //return lettuceConnectionFactory;
    }

    public Map<String, StringRedisTemplate> redisTemplate() {
        List<RedisClusterBean> redisClusterBean = getRedisClusterBean();
        Map<String, StringRedisTemplate> redisTemplateMap = new HashMap<>();
        for (int i = 0; i < redisClusterBean.size(); i++) {
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(redisConnectionFactory(redisClusterBean.get(i)));
//            stringRedisTemplate.setHashKeySerializer();
//            stringRedisTemplate.setHashValueSerializer();
//            stringRedisTemplate.setKeySerializer();
//            stringRedisTemplate.setValueSerializer();
            stringRedisTemplate.afterPropertiesSet();
            redisTemplateMap.put(i + "", stringRedisTemplate);
        }
        return redisTemplateMap;
    }


    // @Bean("redisConnectionFactoryTest")
    public RedisConnectionFactory redisConnectionFactoryTest() {
        List<RedisClusterBean> redisClusterBean = getRedisClusterBean();
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        String[] sub = redisClusterBean.get(0).getNodes().split(",");
        Set<RedisNode> nodeList = new HashSet<>(sub.length);
        String[] tmp;
        for (String s : sub) {
            tmp = s.split(":");
            nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
        }
        redisSentinelConfiguration.setSentinels(nodeList);
        redisSentinelConfiguration.setMaster(redisClusterBean.get(0).getMaster());
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, jedisPoolConfig());
        System.out.println("---------------------jedisConnectionFactory初始化完成！！！");
        return jedisConnectionFactory;
    }

    //@Bean("list")
    public List list() {
        List<RedisClusterBean> redisClusterBean = getRedisClusterBean();
        System.out.println("---------------------list初始化完成！！！");
        return redisClusterBean;
    }
}*/
