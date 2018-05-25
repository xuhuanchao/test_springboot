package com.xhc.test.springboot.rest;

import com.xhc.test.springboot.entity.Student;
import com.xhc.test.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xuhuanchao on 2018/5/22.
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {

    private static final String template = "Hello, %s!";

    @Autowired
    private StudentService studentService;

    @RequestMapping("/findAllStudents")
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    @RequestMapping("/findStudentByAge")
    public List<Student> findStudentByAge(@RequestParam(value = "age1") int age1,
                                           @RequestParam(value = "age2") int age2){
        return studentService.findStudentByAgeRange(age1, age2);
    }

    @RequestMapping("/findStudentById")
    public Student findStudentById(@RequestParam(value = "id") long id){
        return studentService.findStduentById(id);
    }
}
