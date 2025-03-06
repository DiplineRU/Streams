package com.example.Streams.service;

import com.example.Streams.model.Faculty;
import com.example.Streams.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile("production")
public class FacultService {

    private final Logger logger = LoggerFactory.getLogger(FacultService.class);
    private final FacultyRepository facultyRepository;

    public FacultService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method for get all faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameIgnoreCase(String name) {
        logger.info("Was invoked method for find by name ignore case");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> findByColorIgnoreCase(String color) {
        logger.info("Was invoked method for find by color ignore case");
        return facultyRepository.findByColorIgnoreCase(color);
    }
}