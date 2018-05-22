package com.xhc.test.springboot.service;

import com.xhc.test.springboot.dao.StudentDao;
import com.xhc.test.springboot.dao.StudentRepository;
import com.xhc.test.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentDao studentDao;

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public List<Student> findStudentByAgeRange(int age1, int age2) {
        return studentRepository.findByAgeRange(age1, age2);
    }

    @Override
    public Student findStduentById(long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.get();
    }
}
