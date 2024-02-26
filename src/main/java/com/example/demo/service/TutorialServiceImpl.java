package com.example.demo.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.utils.Utils;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public Object findByPublished(boolean published, int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> data = this.tutorialRepository.findByPublished(published, paging);

        return Utils.setDataPagination(data);
    }

    @Override
    public Object findByTitleContaining(String title, int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> data = this.tutorialRepository.findByTitleContaining(title, paging);

        return Utils.setDataPagination(data);
    }

    @Override
    public Map<String, Object> getAllTutorials(String title, int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> data;

        if (title == null) {
            data = this.tutorialRepository.findAll(paging);
        } else {
            data = this.tutorialRepository.findByTitleContaining(title, paging);
        }

        return Utils.setDataPagination(data);
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
