package com.xhc.test.springboot.dao;

import com.xhc.test.springboot.entity.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public interface StudentDao {

    List<Student> findByAgeRange(int age1, int age2);

    List<Student> getAllStudents();
}
