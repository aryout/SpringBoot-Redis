package com.faceyee.configuration;

import com.faceyee.utils.JedisConfig;
import com.faceyee.utils.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by 97390 on 8/21/2018.
 */
@Configuration
@EnableCaching
public class JedisConfiguration extends CachingConfigurerSupport {

    @Autowired
    private JedisConfig redisConfig;

    @Autowired
    private JedisParamConfiguration jedisParamConfiguration;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean // 配置工厂
    public JedisConnectionFactory jedisConnectionFactory() {
        switch (redisConfig.getType()) {
            case "单机":
                return new JedisConnectionFactory(jedisParamConfiguration.jedisPoolConfig());
            case "集群":
                return new JedisConnectionFactory(jedisParamConfiguration.redisClusterConfiguration(), jedisParamConfiguration.jedisPoolConfig());
            case "哨兵":
                return new JedisConnectionFactory(jedisParamConfiguration.sentinelConfiguration(), jedisParamConfiguration.jedisPoolConfig());
        }
        return null;
    }

    @Bean // spring 和 jedis 对话的 RedisTemplate
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate template = new RedisTemplate();

        // 配置Serializer,有不同的Serializer. 设置数据存入 redis 的序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);


        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        // 开启事务
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(jedisConnectionFactory);
        return builder.build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean(name = "redisUtil") // 注入封装RedisTemplate
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}
