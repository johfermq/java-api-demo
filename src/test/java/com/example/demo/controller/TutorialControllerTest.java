package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;

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

import com.example.demo.dto.PagingDto;
import com.example.demo.model.Category;
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
                                .categoryId(1L)
                                .category(Category.builder().id(1L).name("category test").build())
                                .build());

                PagingDto<Tutorial> paging = PagingDto.<Tutorial>builder().currentPage(0).totalItems(1).totalPages(1)
                                .data(tutorials).build();

                Mockito.when(this.tutorialService.getAllTutorials(any(), anyInt(), anyInt())).thenReturn(paging);
        }

        @Test
        @DisplayName("Prueba para obtener la respuesta de todos los tutoriales paginados")
        void testGetAllTutorials() throws Exception {

                MvcResult result = this.mockMvc.perform(
                                get("/api/tutorials")
                                                .contentType(MediaType.APPLICATION_JSON).param("page", "0")
                                                .param("size", "5").param("search", ""))
                                .andExpect(status().isOk())
                                .andReturn();

                assertEquals("{\"status\":200,\"message\":\"Successfully!\",\"error\":\"\",\"data\":{\"currentPage\":0,\"totalItems\":1,\"totalPages\":1,\"data\":[{\"id\":1,\"title\":\"title test\",\"description\":\"description test\",\"published\":true,\"categoryId\":1,\"category\":{\"id\":1,\"name\":\"category test\"}}]}}",
                                result.getResponse().getContentAsString());
        }
}
