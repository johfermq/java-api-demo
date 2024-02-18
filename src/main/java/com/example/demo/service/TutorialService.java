package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.Tutorial;

public interface TutorialService {

    Object findByPublished(boolean published, int page, int size);

    Object findByTitleContaining(String title, int page, int size);

    Object getAllTutorials(String title, int page, int size);

    Optional<Tutorial> findById(long id);

    Tutorial save(Tutorial tutorial);

    void deleteById(long id);
}
