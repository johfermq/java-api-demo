package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Tutorial;

@Repository
public interface CustomTutorialRepository {

    void saveTutorials(List<Tutorial> tutorials);

}
