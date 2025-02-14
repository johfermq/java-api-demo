package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PagingDto;
import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.specifications.GetAllTutorialsSpecification;
import com.example.demo.utils.Pagination;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialServiceImpl(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public PagingDto<Tutorial> findByPublished(boolean published, int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> data = this.tutorialRepository.findByPublished(published, paging);

        return Pagination.<Tutorial>setDataPagination(data);
    }

    @Override
    public PagingDto<Tutorial> findByTitleContaining(String title, int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> data = this.tutorialRepository.findByTitleContaining(title, paging);

        return Pagination.<Tutorial>setDataPagination(data);
    }

    @Override
    public PagingDto<Tutorial> getAllTutorials(GetAllTutorialsSpecification specification, int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        Page<Tutorial> data = this.tutorialRepository.findAll(specification, paging);

        return Pagination.<Tutorial>setDataPagination(data);
    }

    @Override
    public Optional<Tutorial> findById(long id) {
        return this.tutorialRepository.findByIdAndFetchCategory(id);
    }

    @Transactional
    @Override
    public Tutorial save(Tutorial tutorial) {

        List<Tutorial> tutorials = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            tutorials.add(Tutorial.builder().title("title " + i).description("desc " + i)
                    .published(false).categoryId(Long.valueOf(i))
                    .build());
        }

        this.tutorialRepository.saveTutorials(tutorials);

        return tutorial;
    }

    @Override
    public void deleteById(long id) {
        this.tutorialRepository.deleteById(id);
    }
}
