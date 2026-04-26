package com.bidweather.backend_core.controller;

import com.bidweather.backend_core.dto.response.CategoryResponseDto;
import com.bidweather.backend_core.service.query.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryQueryService categoryQueryService;

    @GetMapping("")
    public ResponseEntity<CategoryResponseDto> getCategories() {
        return ResponseEntity.ok(categoryQueryService.getAllCategories());
    }
}
