package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ResponseDto;

public class ResponseHandler {

    public static final String SUCCESSFULLY = "Successfully!";

    public static final String FAILED = "Failed!";

    private ResponseHandler() {
        throw new IllegalStateException("ResponseHandler class");
    }

    public static <T> ResponseEntity<ResponseDto<T>> setResponse(String message, HttpStatus status, T data,
            String error) {
        ResponseDto<T> response = ResponseDto.<T>builder()
                .status(status.value())
                .message(message)
                .error(error)
                .data(data)
                .build();

        return new ResponseEntity<>(response, status);
    }

}
