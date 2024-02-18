package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Tutorial;

public interface TutorialService {
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);

    List<Tutorial> getAllTutorials(String title);

    Optional<Tutorial> findById(long id);

    Tutorial save(Tutorial tutorial);

    void deleteById(long id);
}
