package com.xhc.test.springboot.commandrunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by xuhuanchao on 2018/5/23.
 */
@Component
@Order(value=2)
public class RedisTester implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");
    }
}
