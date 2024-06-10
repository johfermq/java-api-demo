package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.PagingDto;
import com.example.demo.model.Category;
import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.specifications.GetAllTutorialsSpecification;

@ActiveProfiles("test")
@SpringBootTest
class TutorialServiceTest {

    @Autowired
    TutorialService tutorialService;

    @MockBean
    TutorialRepository tutorialRepository;

    private GetAllTutorialsSpecification specification;

    @BeforeEach
    void setUp() {
        this.specification = new GetAllTutorialsSpecification("title test");

        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(Tutorial.builder()
                .title("title test")
                .description("description test")
                .published(true)
                .categoryId(1L)
                .category(Category.builder().id(1L).name("category test").build())
                .build());

        Pageable pageable = PageRequest.of(0, 5);

        Mockito.when(this.tutorialRepository.findAll(this.specification, pageable))
                .thenReturn(new PageImpl<Tutorial>(tutorials, pageable, 1));

        Mockito.when(this.tutorialRepository.findByTitleContaining("title test", pageable))
                .thenReturn(new PageImpl<Tutorial>(tutorials, pageable, 1));
    }

    @Test
    @DisplayName("Prueba para obtener todos los tutoriales paginados")
    void testGetAllTutorials() {
        PagingDto<Tutorial> data = this.tutorialService.getAllTutorials(this.specification, 0, 5);

        assertNotNull(data.getData());
        assertNotNull(data.getData().get(0).getCategory());
        assertEquals(0, data.getCurrentPage());
        assertEquals(1, data.getTotalItems());
        assertEquals(1, data.getTotalPages());
    }

    @Test
    @DisplayName("Prueba para obtener todos los tutoriales por titulo paginados")
    void testGetAllTutorialsByTitle() {
        PagingDto<Tutorial> data = this.tutorialService.getAllTutorials(this.specification, 0, 5);

        assertNotNull(data.getData());
        assertEquals(0, data.getCurrentPage());
        assertEquals(1, data.getTotalItems());
        assertEquals(1, data.getTotalPages());
    }
}
