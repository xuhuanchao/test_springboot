package com.xhc.test.springboot;

import com.xhc.test.springboot.util.RedisUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * Created by xuhuanchao on 2018/5/22.
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class Application  implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static Map appModule = new HashMap();

    public static ApplicationContext ctx;

    public static void start(String args[]) {
        String path = Main.class.getClassLoader().getResource("./").getPath();
        PropertyConfigurator.configure(path+ "config/log4j.properties");


        try {
            path = Application.class.getResource("").getPath();
            File file = new File(path);
            File[] files = file.listFiles();
            for(File f : files){
                if(!f.isDirectory()){
                    String clazzName = f.getName();
                    if(clazzName.startsWith("SpringConfig")){
                        String fname = f.getName().replace(".class", "");
                        Class<?> clazz = Class.forName("com.xhc.test.springboot." + fname);
                        log.info("class name :" + clazz);
                        boolean isConfig = clazz.isAnnotationPresent(Configuration.class);
                        String moduleName = clazz.toString().substring(clazz.toString().lastIndexOf("_")+1);

                        if(isConfig){
                            appModule.put(moduleName, true);
                        }else{
                            appModule.put(moduleName, false);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        ctx = SpringApplication.run(Application.class, args);
        log.info("ctx complete.");
    }


    @Bean
    public RedisUtil redisUtil(){
        if(appModule.containsKey("Redis") && appModule.get("Redis").equals(true)){
            return new RedisUtil();
        }else {
            return null;
        }
    }


    @Override
    public void run(String... strings) throws Exception {

        log.info("CommandLineRunner work");
    }


}
