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

import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.Tutorial;
import com.example.demo.request.TutorialRequest;
import com.example.demo.service.TutorialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TutorialController {

    private final TutorialService tutorialService;

    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<Object> getAllTutorials(@RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            Object response = this.tutorialService.getAllTutorials(title, page, size);

            return ResponseHandler.setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.OK,
                    response,
                    "");
        } catch (Exception e) {
            return ResponseHandler.setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Object> createTutorial(@Valid @RequestBody TutorialRequest tutorial) {
        try {
            Tutorial tutorialSaved = this.tutorialService
                    .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));

            return ResponseHandler.setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.CREATED,
                    tutorialSaved,
                    "");
        } catch (Exception e) {
            return ResponseHandler.setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Object> getTutorialById(@PathVariable("id") long id) {
        try {
            Optional<Tutorial> tutorialData = this.tutorialService.findById(id);

            if (tutorialData.isPresent()) {
                return ResponseHandler.setResponse(
                        ResponseHandler.SUCCESSFULLY,
                        HttpStatus.OK,
                        tutorialData.get(),
                        "");
            } else {
                return ResponseHandler.setResponse(
                        ResponseHandler.FAILED,
                        HttpStatus.NOT_FOUND,
                        tutorialData,
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

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Object> updateTutorial(@PathVariable("id") long id,
            @Valid @RequestBody TutorialRequest tutorial) {

        try {
            Optional<Tutorial> tutorialData = this.tutorialService.findById(id);

            if (tutorialData.isPresent()) {
                Tutorial myTutorial = tutorialData.get();
                myTutorial.setTitle(tutorial.getTitle());
                myTutorial.setDescription(tutorial.getDescription());
                myTutorial.setPublished(tutorial.isPublished());

                return ResponseHandler.setResponse(
                        ResponseHandler.SUCCESSFULLY,
                        HttpStatus.OK,
                        this.tutorialService.save(myTutorial),
                        "");
            } else {
                return ResponseHandler.setResponse(
                        ResponseHandler.FAILED,
                        HttpStatus.NOT_FOUND,
                        tutorialData,
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
    public ResponseEntity<Object> deleteTutorial(@PathVariable("id") long id) {
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
    public ResponseEntity<Object> findByPublished(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            Object response = this.tutorialService.findByPublished(true, page, size);

            return ResponseHandler.setResponse(
                    ResponseHandler.SUCCESSFULLY,
                    HttpStatus.OK,
                    response,
                    "");
        } catch (Exception e) {
            return ResponseHandler.setResponse(
                    ResponseHandler.FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    e.getMessage());
        }
    }

}