package com.faceyee.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by 97390 on 8/22/2018.
 */
@Configuration
@PropertySource("classpath:config/redis.properties") // 不是用此注解匹配路径的话,只会默认扫描application这个文件而已
@ConfigurationProperties(prefix = "redis.config")
// @ConfigurationProperties("user") 可直接映射成一个类
@Data
public class JedisConfig {

    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWaitMillis;
    private boolean testWhileIdle;
    private int numTestsPerEvictionRun;
    private int timeBetweenEvictionRunsMillis;
    private boolean testOnBorrow;
    private boolean testOnReturn;

    private String type;
    private String host;
    private String name;
    private int port;
    private int timeout;
    private int weight;
    private String password;
    private String clusterNodes;
    private Integer maxRedirectsac;
}