package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PagingDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.Tutorial;
import com.example.demo.request.TutorialRequest;
import com.example.demo.service.TutorialService;
import com.example.demo.specifications.GetAllTutorialsSpecification;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TutorialController {

    private final TutorialService tutorialService;

    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<ResponseDto<PagingDto<Tutorial>>> getAllTutorials(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            GetAllTutorialsSpecification specification = new GetAllTutorialsSpecification(search);

            PagingDto<Tutorial> response = this.tutorialService.getAllTutorials(specification, page, size);

            return ResponseHandler.<PagingDto<Tutorial>>setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.OK,
                    response,
                    "");
        } catch (Exception e) {
            return ResponseHandler.<PagingDto<Tutorial>>setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<ResponseDto<Tutorial>> createTutorial(@Valid @RequestBody TutorialRequest request) {
        try {
            Boolean published = request.getPublished();
            Tutorial tutorialSaved = this.tutorialService
                    .save(Tutorial.builder().title(request.getTitle()).description(request.getDescription())
                            .published(published != null && published).categoryId(request.getCategoryId())
                            .build());

            return ResponseHandler.<Tutorial>setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.CREATED,
                    tutorialSaved,
                    "");
        } catch (Exception e) {
            return ResponseHandler.<Tutorial>setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<ResponseDto<Tutorial>> getTutorialById(@PathVariable("id") long id) {
        try {
            Optional<Tutorial> tutorialData = this.tutorialService.findById(id);

            if (tutorialData.isPresent()) {
                return ResponseHandler.<Tutorial>setResponse(
                        ResponseHandler.SUCCESSFULLY,
                        HttpStatus.OK,
                        tutorialData.get(),
                        "");
            } else {
                return ResponseHandler.<Tutorial>setResponse(
                        ResponseHandler.FAILED,
                        HttpStatus.NOT_FOUND,
                        null,
                        "Not found");
            }
        } catch (Exception e) {
            return ResponseHandler.<Tutorial>setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<ResponseDto<Tutorial>> updateTutorial(@PathVariable("id") long id,
            @Valid @RequestBody TutorialRequest tutorial) {

        try {
            Optional<Tutorial> tutorialData = this.tutorialService.findById(id);

            if (tutorialData.isPresent()) {
                Tutorial myTutorial = tutorialData.get();
                myTutorial.setTitle(tutorial.getTitle());
                myTutorial.setDescription(tutorial.getDescription());
                myTutorial.setPublished(tutorial.getPublished());

                return ResponseHandler.<Tutorial>setResponse(
                        ResponseHandler.SUCCESSFULLY,
                        HttpStatus.OK,
                        this.tutorialService.save(myTutorial),
                        "");
            } else {
                return ResponseHandler.<Tutorial>setResponse(
                        ResponseHandler.FAILED,
                        HttpStatus.NOT_FOUND,
                        null,
                        "Not found");
            }
        } catch (Exception e) {
            return ResponseHandler.setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<ResponseDto<Tutorial>> deleteTutorial(@PathVariable("id") long id) {
        try {
            this.tutorialService.deleteById(id);

            return ResponseHandler.setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.OK,
                    null,
                    "");
        } catch (Exception e) {
            return ResponseHandler.setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<ResponseDto<PagingDto<Tutorial>>> findByPublished(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            PagingDto<Tutorial> response = this.tutorialService.findByPublished(true, page, size);

            return ResponseHandler.<PagingDto<Tutorial>>setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.OK,
                    response,
                    "");
        } catch (Exception e) {
            return ResponseHandler.<PagingDto<Tutorial>>setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

}