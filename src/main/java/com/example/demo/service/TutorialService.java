package com.example.demo.service;

import java.util.Optional;

import com.example.demo.dto.PagingDto;
import com.example.demo.model.Tutorial;
import com.example.demo.specifications.GetAllTutorialsSpecification;

public interface TutorialService {

    PagingDto<Tutorial> findByPublished(boolean published, int page, int size);

    PagingDto<Tutorial> findByTitleContaining(String title, int page, int size);

    PagingDto<Tutorial> getAllTutorials(GetAllTutorialsSpecification specification, int page, int size);

    Optional<Tutorial> findById(long id);

    Tutorial save(Tutorial tutorial);

    void deleteById(long id);
}
