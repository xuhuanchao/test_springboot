package com.xhc.test.springboot.schedule;

import com.xhc.test.springboot.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/23.
 */
@Component
@ConfigurationProperties(prefix = "student")
public class CheckStudentCacheSchedule implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(CheckStudentCacheSchedule.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private String queryAllKey;

    private String queryByIdKey;

    private String queryByAgeRangeKey;


    @Scheduled(cron = "0 * * * * *")  //秒、分钟、小时、天、月、星期（与天只能出现一个）、年
    public void queryStudentCache() {
        List<Student> list = (List<Student>)redisTemplate.opsForValue().get(queryAllKey);
        if(list != null){
            log.info("Get queryAllStudent result from redis. Result`s number is "+ list.size());
        }

        Student s = (Student)redisTemplate.opsForValue().get(queryByIdKey);
        if(s != null){
            log.info("Get queryById result from redis. The student is " + s.toString());
        }

        List<Student> list2 = (List<Student>)redisTemplate.opsForValue().get(queryByAgeRangeKey);
        if(list2 != null){
            log.info("Get queryByAgeRange result from redis. Result`s number is " + list.size());
        }

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("queryAllKey:" + queryAllKey + ", queryByIdKey:" + queryByIdKey + ", queryByAgeRangeKey:"+queryByAgeRangeKey);
    }

    // getter setter
    public String getQueryAllKey() {
        return queryAllKey;
    }

    public void setQueryAllKey(String queryAllKey) {
        this.queryAllKey = queryAllKey;
    }

    public String getQueryByIdKey() {
        return queryByIdKey;
    }

    public void setQueryByIdKey(String queryByIdKey) {
        this.queryByIdKey = queryByIdKey;
    }

    public String getQueryByAgeRangeKey() {
        return queryByAgeRangeKey;
    }

    public void setQueryByAgeRangeKey(String queryByAgeRangeKey) {
        this.queryByAgeRangeKey = queryByAgeRangeKey;
    }
}
