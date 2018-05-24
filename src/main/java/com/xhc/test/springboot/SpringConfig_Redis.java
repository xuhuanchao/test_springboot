package com.xhc.test.springboot;

import com.xhc.test.springboot.consumer.RedisChatReceiver;
import com.xhc.test.springboot.consumer.RedisOrderReceiver;
import com.xhc.test.springboot.schedule.CheckStudentCacheSchedule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xuhuanchao on 2018/5/23.
 */
@Configuration
@PropertySource(value = "classpath:/config/redis.properties")
@PropertySource(value= "classpath:config/redisKey.properties")
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
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }



    @Bean
    CheckStudentCacheSchedule checkStudentCacheSchedule(){
        return new CheckStudentCacheSchedule();
    }
}
