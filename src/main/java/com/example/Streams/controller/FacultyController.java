package com.example.Streams.controller;


import com.example.Streams.service.FacultService;
import com.example.Streams.repository.FacultyRepository;
import com.example.Streams.model.Faculty;
import com.example.Streams.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;

@RestController
@RequestMapping("Faculty")
public class FacultyController {
    private final FacultService facultService;
    private final FacultyRepository facultyRepository;

    public FacultyController(FacultService facultService, FacultyRepository facultyRepository) {
        this.facultService = facultService;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping("{id}") // GET
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping //POST
    public Faculty createStudent(@RequestBody Faculty faculty) {
        return facultService.createFaculty(faculty);
    }

    @GetMapping //GET
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultService.getAllFaculty());
    }

    @GetMapping("/searchNameOrColor")
    public ResponseEntity<Collection<Faculty>> searchFaculties(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String color) {
        if (name != null) {
            return ResponseEntity.ok(facultService.findByNameIgnoreCase(name));
        }
        if (color != null) {
            return ResponseEntity.ok(facultService.findByColorIgnoreCase(color));
        }
        return ResponseEntity.ok(null); // Возвращаем пустой список, если параметры не указаны
    }

    @PutMapping //PUT
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}") //DELETE
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        Faculty faculty = facultService.findFaculty(id);
        if (faculty == null || faculty.getStudents() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty.getStudents());
    }

    @GetMapping("/longest-name")
    public String nameSize() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("Факультетов нет");
    }
}