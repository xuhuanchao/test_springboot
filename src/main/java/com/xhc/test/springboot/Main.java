package com.xhc.test.springboot;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static ApplicationContext ctx ;

    public static void main(String args[]) {
        Application.start(args);
//        String path = Main.class.getClassLoader().getResource("./").getPath();
//        System.out.println(path);
//        PropertyConfigurator.configure(path+ "config/log4j.properties");
//        ctx = SpringApplication.run(Application.class, args);
    }
}
