package com.xhc.test.springboot.service;

import com.xhc.test.springboot.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public interface StudentService {

    public List<Student> findAllStudents();

    public List<Student> findStudentByAgeRange(int age1, int age2);

    public Student findStduentById(long id);

    public void addStudent(Student student);

    public Page<Student> findAllByPage(Pageable pageable);

    public List<Student> findByLastNameLike(String lastName);
}
