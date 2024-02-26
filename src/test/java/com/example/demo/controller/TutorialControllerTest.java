package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.Tutorial;
import com.example.demo.service.TutorialService;

@WebMvcTest(TutorialController.class)
class TutorialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TutorialService tutorialService;

    @BeforeEach
    void setUp() {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        tutorials.add(Tutorial.builder()
                .id(1)
                .title("title test")
                .description("description test")
                .published(true)
                .source("source test")
                .build());

        Map<String, Object> data = new HashMap<>();
        data.put("data", tutorials);
        data.put("currentPage", 0);
        data.put("totalItems", 1);
        data.put("totalPages", 1);

        Mockito.when(this.tutorialService.getAllTutorials(null, 0, 5)).thenReturn(data);
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Prueba para obtener la respuesta de todos los tutoriales paginados")
    void testGetAllTutorials() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                "{\"data\":{\"totalItems\":1,\"data\":[{\"id\":1,\"title\":\"title test\",\"description\":\"description test\",\"published\":true,\"source\":\"source test\"}],\"totalPages\":1,\"currentPage\":0},\"message\":\"Successfully!\",\"error\":\"\",\"status\":200}");
    }
}
