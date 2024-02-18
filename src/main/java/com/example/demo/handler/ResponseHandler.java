package com.example.demo.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static final String SUCCESSFULLY = "Successfully!";

    public static final String FAILED = "Failed!";

    public static ResponseEntity<Object> setResponse(String message, HttpStatus status, Object responseObj,
            String error) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("error", error);

        return new ResponseEntity<>(map, status);
    }

}
