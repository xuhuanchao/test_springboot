package com.xhc.test.springboot.dao;

import com.xhc.test.springboot.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
@Repository
public class StudentDaoImpl implements StudentDao{

    private static final Logger log = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Student> findByAgeRange(int age1, int age2) {
        List<Student> list = jdbcTemplate.query(
                "SELECT id, first_name, last_name, level, age FROM STUDENT WHERE age >= ? and age <= ?", new Object[]{age1, age2},
                (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("level"), rs.getInt("age"))
        );
//        query.forEach(customer -> log.info(customer.toString()));
        return list;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> list = jdbcTemplate.query(
                "SELECT id, first_name, last_name, level, age FROM STUDENT",
                (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("level"), rs.getInt("age"))
        );
        return list;
    }
}
