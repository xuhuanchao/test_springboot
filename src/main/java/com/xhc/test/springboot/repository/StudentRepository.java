package com.xhc.test.springboot.repository;

import com.xhc.test.springboot.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public interface StudentRepository extends CrudRepository<Student, Long>{

    List<Student> findByLastName(String lastName);

    @Query(value = "SELECT * FROM STUDENT WHERE AGE >= ?1 and AGE <=?2", nativeQuery = true)
    List<Student> findByAgeRange(int age1, int age2);

    @Query(value = "SELECT * FROM STUDENT", nativeQuery = true)
    List<Student> getAllStudents();

}
