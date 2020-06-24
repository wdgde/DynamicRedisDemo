package com.example.demo.config;

import com.example.demo.bean.RedisClusterBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: DynamicRedisDemo
 * @description:
 * @author: wdgde
 * @create: 2020-06-19 08:55
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.my.test.cluster")
public class RedisSentinelConfig {

    private List<RedisClusterBean> redisList = new ArrayList<>();

    public List<RedisClusterBean> getRedisList() {
        return redisList;
    }

    public void setRedisList(List<RedisClusterBean> redisList) {
        this.redisList = redisList;
    }

    public class SentinelRedisConnectionFactory {

        private List<RedisConnectionFactory> redisConnectionFactoryList;

        public List<RedisConnectionFactory> getRedisConnectionFactoryList() {
            return redisConnectionFactoryList;
        }

        public void setRedisConnectionFactoryList(List<RedisConnectionFactory> redisConnectionFactoryList) {
            this.redisConnectionFactoryList = redisConnectionFactoryList;
        }
    }

    public class SentinelStringRedisTemplate {
        private List<StringRedisTemplate> stringRedisTemplateList;

        public List<StringRedisTemplate> getStringRedisTemplateList() {
            return stringRedisTemplateList;
        }

        public void setStringRedisTemplateList(List<StringRedisTemplate> stringRedisTemplateList) {
            this.stringRedisTemplateList = stringRedisTemplateList;
        }
    }

    @ConfigurationProperties(prefix = "spring.my.cluster.redis.poolconfig")
    @Bean("myjedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        System.out.println("=======================jedispool初始化完成");
        return new JedisPoolConfig();
    }

    @Bean("sentinelRedisConnectionFactory")
    public SentinelRedisConnectionFactory sentinelRedisConnectionFactory(@Qualifier("myjedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
        SentinelRedisConnectionFactory sentinelRedisConnectionFactory = new SentinelRedisConnectionFactory();
        List<RedisConnectionFactory> redisConnectionFactoryList = new ArrayList<>();
        for (int i = 0; i < redisList.size(); i++) {
            RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
            String[] sub = redisList.get(i).getNodes().split(",");
            Set<RedisNode> nodeList = new HashSet<>(sub.length);
            String[] tmp;
            for (String s : sub) {
                tmp = s.split(":");
                nodeList.add(new RedisNode(tmp[0], Integer.valueOf(tmp[1])));
            }
            redisSentinelConfiguration.setSentinels(nodeList);
            redisSentinelConfiguration.setMaster(redisList.get(i).getMaster());
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, jedisPoolConfig);
            jedisConnectionFactory.setHostName(redisList.get(i).getHost());
            jedisConnectionFactory.setPort(redisList.get(i).getPort());
            jedisConnectionFactory.setDatabase(redisList.get(i).getDatabase());
            redisConnectionFactoryList.add(jedisConnectionFactory);
        }
        sentinelRedisConnectionFactory.setRedisConnectionFactoryList(redisConnectionFactoryList);
        System.out.println("=======================sentinelRedisConnectionFactory初始化完成！！！");
        return sentinelRedisConnectionFactory;
    }

    @Bean("sentinelStringRedisTemplate")
    public SentinelStringRedisTemplate sentinelStringRedisTemplate(@Qualifier("sentinelRedisConnectionFactory") SentinelRedisConnectionFactory sentinelRedisConnectionFactory) {
        SentinelStringRedisTemplate sentinelStringRedisTemplate = new SentinelStringRedisTemplate();
        List<StringRedisTemplate> stringRedisTemplateList = new ArrayList<>();
        for (int i = 0; i < sentinelRedisConnectionFactory.getRedisConnectionFactoryList().size(); i++) {
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(sentinelRedisConnectionFactory.getRedisConnectionFactoryList().get(i));
            stringRedisTemplate.afterPropertiesSet();
            stringRedisTemplateList.add(stringRedisTemplate);
        }
        sentinelStringRedisTemplate.setStringRedisTemplateList(stringRedisTemplateList);
        System.out.println("=======================sentinelStringRedisTemplate初始化完成！！！");
        return sentinelStringRedisTemplate;
    }

}