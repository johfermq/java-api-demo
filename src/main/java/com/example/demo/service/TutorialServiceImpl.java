package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return this.tutorialRepository.findByPublished(published);
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        return this.tutorialRepository.findByTitleContaining(title);
    }

    @Override
    public List<Tutorial> getAllTutorials(String title) {
        if (title == null) {
            return this.tutorialRepository.findAll();
        }

        return this.tutorialRepository.findByTitleContaining(title);
    }

    @Override
    public Optional<Tutorial> findById(long id) {
        return this.tutorialRepository.findById(id);
    }

    @Override
    @SuppressWarnings("null")
    public Tutorial save(Tutorial tutorial) {
        return this.tutorialRepository.save(tutorial);
    }

    @Override
    public void deleteById(long id) {
        this.tutorialRepository.deleteById(id);
    }
}
