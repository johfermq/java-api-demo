package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class TutorialRequest {
    @NotBlank(message = "The title is required.")
    @Size(min = 3, max = 100, message = "The title must be from 3 to 100 characters.")
    private String title;

    @NotBlank(message = "The description is required.")
    @Size(min = 3, max = 100, message = "The description must be from 3 to 100 characters.")
    private String description;

    private Boolean published;

    private Long categoryId;

}
