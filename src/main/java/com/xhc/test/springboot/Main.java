package com.xhc.test.springboot;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {

        String path = Main.class.getClassLoader().getResource("./").getPath();
        System.out.println(path);
        PropertyConfigurator.configure(path+ "config/log4j.properties");
        SpringApplication.run(Application.class, args);
    }
}
