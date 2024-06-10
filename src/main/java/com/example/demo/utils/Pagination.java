package com.example.demo.utils;

import org.springframework.data.domain.Page;

import com.example.demo.dto.PagingDto;

public class Pagination {

    private Pagination() {
        throw new IllegalStateException("Pagination class");
    }

    public static <T> PagingDto<T> setDataPagination(Page<T> page) {
        return PagingDto.<T>builder()
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build();
    }
}
