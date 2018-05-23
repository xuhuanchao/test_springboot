package com.xhc.test.springboot.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xuhuanchao on 2018/5/23.
 */
public class RedisOrderReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisOrderReceiver.class);

    private CountDownLatch latch;

    @Autowired
    public RedisOrderReceiver(CountDownLatch orderCountDownLatch) {
        this.latch = orderCountDownLatch;
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received order <" + message + ">");
        latch.countDown();
    }

}
