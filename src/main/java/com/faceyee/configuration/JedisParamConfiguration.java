package com.faceyee.configuration;

import com.faceyee.utils.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 97390 on 8/21/2018.
 */
@Configuration
public class JedisParamConfiguration {
    @Autowired
    private JedisConfig redisConfig;

    @Bean // JedisPoolConfig 连接池
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfig.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(redisConfig.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisConfig.isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(redisConfig.isTestWhileIdle());
        return jedisPoolConfig;
    }


    @Bean // Redis集群的配置
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        //Set<RedisNode> clusterNodes
        String[] serverArray = redisConfig.getClusterNodes().split(",");

        Set<RedisNode> nodes = new HashSet<RedisNode>();

        for(String ipPort:serverArray){
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
        }

        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(redisConfig.getMaxRedirectsac());

        return redisClusterConfiguration;
    }

    @Bean // 配置redis的哨兵
    public RedisSentinelConfiguration sentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置matser的名称
        RedisNode redisNode = new RedisNode(redisConfig.getHost(), redisConfig.getPort());
        redisNode.setName("mymaster");
        redisSentinelConfiguration.master(redisNode);

//        //配置redis的哨兵sentinel
//        Set<RedisNode> redisNodeSet = new HashSet<>();
//        RedisNode senRedisNode = new RedisNode(senHost1,senPort1); // 需要从配置文件不断读取,待定..
//        redisNodeSet.add(senRedisNode);
//        senRedisNode = new RedisNode(senHost2,senPort2);
//        redisNodeSet.add(senRedisNode);
//
//        redisSentinelConfiguration.setSentinels(redisNodeSet);
        return redisSentinelConfiguration;
    }

}
