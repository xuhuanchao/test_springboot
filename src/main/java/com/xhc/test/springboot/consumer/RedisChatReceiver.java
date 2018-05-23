package com.xhc.test.springboot.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

public class RedisChatReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisChatReceiver.class);

    private CountDownLatch latch;

    @Autowired
    public RedisChatReceiver(CountDownLatch chatCountDownLatch) {
        this.latch = chatCountDownLatch;
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        latch.countDown();
    }

}
