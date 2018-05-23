package com.xhc.test.springboot;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by xuhuanchao on 2018/5/22.
 */
@SpringBootApplication
@EnableScheduling
public class Application  implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static ApplicationContext ctx;

    public static void start(String args[]) {
        String path = Main.class.getClassLoader().getResource("./").getPath();
        PropertyConfigurator.configure(path+ "config/log4j.properties");
        ctx = SpringApplication.run(Application.class, args);
        log.info("ctx complete.");
    }




    @Override
    public void run(String... strings) throws Exception {
        log.info("CommandLineRunner work");
    }
}
