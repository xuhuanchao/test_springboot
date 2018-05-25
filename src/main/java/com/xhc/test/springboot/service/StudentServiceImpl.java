package com.xhc.test.springboot.service;

import com.xhc.test.springboot.repository.StudentDao;
import com.xhc.test.springboot.repository.StudentRepository;
import com.xhc.test.springboot.entity.Student;
import com.xhc.test.springboot.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
@Service
@ConfigurationProperties(prefix = "redis.student")
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private String clazzName = StudentServiceImpl.class.getSimpleName();

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RedisUtil redisUtil;

    private String queryAllKey;

    private String queryByIdKey;

    private String queryByAgeRangeKey;


    @Override
    @Cacheable(value="Student", key="#root.methodName")
    public List<Student> findAllStudents() {
        List<Student> list = studentRepository.findAll();
        return list;
    }

    @Override
    @Cacheable(value="Student", key="#root.methodName + #age1 + #age2")
    public List<Student> findStudentByAgeRange(int age1, int age2) {
        List<Student> list = studentRepository.findByAgeBetween(age1, age2);
        return list;
    }

    @Override
    @Cacheable(value="Student", key="#root.methodName + #id")
    public Student findStduentById(long id) {
        Student s = studentRepository.findById(id);
        return s;
    }


    @Override
    public void addStudent(Student student) {
        Student s = studentRepository.save(student);
    }

    @Override
    @Cacheable(value="Student", key="#root.methodName + #root.args[0]")
    public Page<Student> findAllByPage(Pageable pageable) {
        Page<Student> page  = studentRepository.findAll(pageable);
        return page;
    }

    @Override
    @Cacheable(value="Student", key="#root.methodName + #lastName")
    public List<Student> findByLastNameLike(String lastName) {
        List<Student> list = studentRepository.findByLastNameLike(lastName);
        return list;
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
