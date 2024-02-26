package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;

@ActiveProfiles("test")
@SpringBootTest
class TutorialServiceTest {

    @Autowired
    TutorialService tutorialService;

    @MockBean
    TutorialRepository tutorialRepository;

    @BeforeEach
    void setUp() {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(Tutorial.builder()
                .title("title test")
                .description("description test")
                .published(true)
                .source("source test")
                .build());

        Pageable pageable = PageRequest.of(0, 5);

        Mockito.when(this.tutorialRepository.findAll(pageable)).thenReturn(new PageImpl<Tutorial>(tutorials, pageable, 1));

        Mockito.when(this.tutorialRepository.findByTitleContaining("title test", pageable))
                .thenReturn(new PageImpl<Tutorial>(tutorials, pageable, 1));
    }

    @Test
    @DisplayName("Prueba para obtener todos los tutoriales paginados")
    void testGetAllTutorials() {
        Map<String, Object> data = this.tutorialService.getAllTutorials(null, 0, 5);

        assertNotNull(data.get("data"));
        assertEquals("0", data.get("currentPage").toString());
        assertEquals("1", data.get("totalItems").toString());
        assertEquals("1", data.get("totalPages").toString());
    }

    @Test
    @DisplayName("Prueba para obtener todos los tutoriales por titulo paginados")
    void testGetAllTutorialsByTitle() {
        String title = "title test";

        Map<String, Object> data = this.tutorialService.getAllTutorials(title, 0, 5);

        assertNotNull(data.get("data"));
        assertEquals("0", data.get("currentPage").toString());
        assertEquals("1", data.get("totalItems").toString());
        assertEquals("1", data.get("totalPages").toString());
    }
}
