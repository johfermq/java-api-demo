package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TutorialRequest {
    @NotBlank(message = "The title is required.")
    @Size(min = 3, max = 100, message = "The title must be from 3 to 100 characters.")
    private String title;

    @NotBlank(message = "The description is required.")
    @Size(min = 3, max = 100, message = "The description must be from 3 to 100 characters.")
    private String description;

    private Boolean published;

    private String source;

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
