package com.xhc.test.springboot.repository;

import com.xhc.test.springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public interface StudentRepository extends JpaRepository<Student, Long>{

    List<Student> findByLastName(String lastName);

    List<Student> findByAgeBetween(int age1, int age2);

    List<Student> findByLastNameLike(String lastName);

    List<Student> findAll();

    Student findById(long id);

    @Query(value = "SELECT * FROM STUDENT WHERE id = ?1", nativeQuery = true)
    Student findById2(long id);
}
