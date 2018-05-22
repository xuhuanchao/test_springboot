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

    @RequestMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping("/queryStudentByAge")
    public List<Student> queryStudentByAge(@RequestParam(value = "age1") int age1,
                                           @RequestParam(value = "age2") int age2){
        return studentService.findStudentByAgeRange(age1, age2);
    }

    @RequestMapping("/queryStudentById")
    public Student queryStudentById(@RequestParam(value = "id") long id){
        return studentService.findStduentById(id);
    }
}
