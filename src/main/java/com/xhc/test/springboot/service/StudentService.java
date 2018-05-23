package com.xhc.test.springboot.service;

import com.xhc.test.springboot.entity.Student;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
public interface StudentService {

    public List<Student> getAllStudents();

    public List<Student> findStudentByAgeRange(int age1, int age2);

    public Student findStduentById(long id);

    public void AddStudent(Student student);
}
