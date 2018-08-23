package com.faceyee.utils;

import redis.clients.jedis.Jedis;

/**
 * Created by 97390 on 8/23/2018.
 */

public class connTest {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost",6379);
        // no password
        System.out.println(jedis.ping());
        jedis.close();
    }
}