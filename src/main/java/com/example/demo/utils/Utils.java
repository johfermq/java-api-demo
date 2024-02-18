package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

public class Utils {

    public Utils() {
    }

    public static Object setDataPagination(Page<?> page) {
        Object data = page.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return response;
    }
}
