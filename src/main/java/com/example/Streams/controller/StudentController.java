package com.example.Streams.controller;


import com.example.Streams.repository.StudentRepository;
import com.example.Streams.service.StudentService;
import com.example.Streams.model.Faculty;
import com.example.Streams.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
@RequestMapping("Student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("{id}") // GET
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping //POST
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping //GET
    public ResponseEntity findStudent(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(studentService.findByName(name));
        }
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/getStudentByAge") //GET
    public ResponseEntity getStudentByAgeRange(@RequestParam int minAge,
                                               @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
    }

    @PutMapping //PUT
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}") //DELETE
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null || student.getFaculty() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/names-starting-with-a")
    public List<String> nameWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("А"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("/avg-age-all-students")
    public Double avgAgeAllStudents() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @GetMapping("/step-foure")
    public int getParallelSum() {
        return IntStream.rangeClosed(1, 1_000_000)
                .parallel() // Используем параллельный стрим
                .sum();
    }
}