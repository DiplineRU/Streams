package com.example.Streams.repository;

import com.example.Streams.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByNameIgnoreCase(String name);

    Collection<Student> findStudentByNameContainsIgnoreCase(String name);

    Collection<Student> findAllByNameContainsIgnoreCase(String namePart);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT count(*) FROM students", nativeQuery = true)
    List<StudentByCategory> getStudentsCount();

    @Query(value = "SELECT avg(age) from students;", nativeQuery = true)
    double avgAgeByStudents();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT 5;", nativeQuery = true)
    List<StudentByCategory> getLastFiveStudent();
}
