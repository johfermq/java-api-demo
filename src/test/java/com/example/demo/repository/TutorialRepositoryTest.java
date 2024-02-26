package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Tutorial;

@ActiveProfiles("test")
@DataJpaTest
class TutorialRepositoryTest {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Tutorial tutorial = Tutorial.builder()
                .title("title test")
                .description("description test")
                .published(true)
                .source("source test")
                .build();

        testEntityManager.persist(tutorial);
    }

    @Test
    @Description("Prueba para obtener tutoriales publicados paginados")
    void testFindByPublished() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Tutorial> data = this.tutorialRepository.findByPublished(true, pageable);
        assertEquals(1, data.getContent().size());
    }

    @Test
    @Description("Prueba para obtener tutoriales por el titulo paginados")
    void testFindByTitleContaining() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Tutorial> data = this.tutorialRepository.findByTitleContaining("title test", pageable);
        assertEquals(1, data.getContent().size());
    }
}
