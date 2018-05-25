package com.xhc.test.springboot.util;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuhuanchao on 2018/5/24.
 */
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final static long DEFAULT_TIME = 5;
    private final static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MINUTES;


    public void put(String key, Object value){
        redisTemplate.opsForValue().set(key, value, DEFAULT_TIME, DEFAULT_TIMEUNIT);
    }

    public Object get(Object key){
        return redisTemplate.opsForValue().get(key);
    }


}
