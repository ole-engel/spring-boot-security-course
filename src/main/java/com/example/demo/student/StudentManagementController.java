package com.example.demo.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3, "Anna Smith")
    );

    @GetMapping
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents()");
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("registerNewStudent(" + student + ")");
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("deleteStudent(" + studentId + ")");
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.printf("updateStudent(%s, %s)\n", studentId, student);
    }
}
