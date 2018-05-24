package com.xhc.test.springboot.service;

import com.xhc.test.springboot.repository.StudentDao;
import com.xhc.test.springboot.repository.StudentRepository;
import com.xhc.test.springboot.entity.Student;
import com.xhc.test.springboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
@Service
@ConfigurationProperties(prefix = "redis.student")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentDao studentDao;

//    @Resource
//    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    private String queryAllKey;

    private String queryByIdKey;

    private String queryByAgeRangeKey;


    @Override
    public List<Student> getAllStudents() {
        List<Student> list = null;
        if(redisUtil != null){
            Object o = redisUtil.get("getAllStudents");
            list = (List<Student>)o;
        }else{
            list = studentDao.getAllStudents();
        }

        /*Object o = redisTemplate.opsForValue().get(queryAllKey);
        if(o != null){
            list = (List<Student>)o;
        }else{
            list = studentDao.getAllStudents();
            redisTemplate.opsForValue().set(queryAllKey, list, 5, TimeUnit.MINUTES);
        }*/
        return list;
    }

    @Override
    public List<Student> findStudentByAgeRange(int age1, int age2) {
        List<Student> list = null;
//        Object o = redisTemplate.opsForValue().get(queryByAgeRangeKey+age1+"&"+age2);
//        if(o != null){
//            list = (List<Student>)o;
//        }else{
//            list = studentRepository.findByAgeRange(age1, age2);
//            redisTemplate.opsForValue().set(queryByAgeRangeKey+age1+"&"+age2, list, 5, TimeUnit.MINUTES);
//        }
        return list;
    }

    @Override
    public Student findStduentById(long id) {
        Student s = null;
//        Object o = redisTemplate.opsForValue().get(queryByIdKey+id);
//        if(o != null){
//            s = (Student)o;
//        }else{
//            Optional<Student> student = studentRepository.findById(id);
//            s = student.get();
//            redisTemplate.opsForValue().set(queryByIdKey+id, s, 5, TimeUnit.MINUTES);
//        }
        return s;
    }


    @Override
    public void AddStudent(Student student) {
        Student s = studentRepository.save(student);
    }


    //getter setter
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
