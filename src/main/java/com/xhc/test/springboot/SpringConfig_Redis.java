package com.xhc.test.springboot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhc.test.springboot.aop.RedisCache;
import com.xhc.test.springboot.consumer.RedisChatReceiver;
import com.xhc.test.springboot.consumer.RedisOrderReceiver;
import com.xhc.test.springboot.schedule.CheckStudentCacheSchedule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xuhuanchao on 2018/5/23.
 */
@Configuration
@PropertySource(value = "classpath:/config/redis.properties")
@PropertySource(value= "classpath:config/redisKey.properties")
@EnableCaching
public class SpringConfig_Redis {

    @Value("${redis.topic.chat}")
    private String topicChat;

    @Value("${redis.topic.order}")
    private String topicOrder;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter chatListenerAdapter,
                                            MessageListenerAdapter orderListenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(chatListenerAdapter, new PatternTopic(topicChat));
        container.addMessageListener(orderListenerAdapter, new PatternTopic(topicOrder));
        return container;
    }

    @Bean
    MessageListenerAdapter chatListenerAdapter(RedisChatReceiver chatReceiver) {
        return new MessageListenerAdapter(chatReceiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter orderListenerAdapter(RedisOrderReceiver orderReceiver){
        return new MessageListenerAdapter(orderReceiver, "receiveMessage");
    }

    @Bean
    RedisChatReceiver chatReceiver(CountDownLatch chatCountDownLatch) {
        return new RedisChatReceiver(chatCountDownLatch);
    }

    @Bean
    RedisOrderReceiver orderReceiver(CountDownLatch orderCountDownLatch){
        return new RedisOrderReceiver(orderCountDownLatch);
    }

    @Bean
    CountDownLatch chatCountDownLatch() {
        return new CountDownLatch(1);
    }

    @Bean
    CountDownLatch orderCountDownLatch(){
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, String> template = new RedisTemplate<>();

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    CheckStudentCacheSchedule checkStudentCacheSchedule(){
        return new CheckStudentCacheSchedule();
    }

    @Bean
    RedisCache redisCache(){
        return new RedisCache();
    }

    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
        //ClassLoader loader = this.getClass().getClassLoader();
        //JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
        //RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
        //RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        //设置默认超过期时间是30秒
        defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
        return cacheManager;
    }
}
