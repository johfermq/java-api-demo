package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Category;
import com.example.demo.model.Tutorial;
import com.example.demo.specifications.GetAllTutorialsSpecification;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TutorialRepositoryTest {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .name("category test")
                .build();

        Tutorial tutorial = Tutorial.builder()
                .title("title test")
                .description("description test")
                .published(true)
                .categoryId(1L)
                .build();

        testEntityManager.persist(category);
        testEntityManager.persist(tutorial);
    }

    @AfterEach
    void setDown() {
    }

    @Test
    @Description("Prueba para obtener tutoriales paginados")
    void testFindAll() {
        GetAllTutorialsSpecification specification = new GetAllTutorialsSpecification("title test");

        Pageable pageable = PageRequest.of(0, 5);

        Page<Tutorial> data = this.tutorialRepository.findAll(specification, pageable);
        assertEquals(1, data.getContent().size());
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
