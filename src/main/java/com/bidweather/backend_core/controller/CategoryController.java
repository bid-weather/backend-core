package com.bidweather.backend_core.controller;

import com.bidweather.backend_core.dto.response.CategoryResponseDto;
import com.bidweather.backend_core.dto.response.SubcategoryResponseDto;
import com.bidweather.backend_core.service.query.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryQueryService categoryQueryService;

    @GetMapping("")
    public ResponseEntity<CategoryResponseDto> getCategories() {
        return ResponseEntity.ok(categoryQueryService.getAllCategories());
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<SubcategoryResponseDto> getSubcategoriesByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryQueryService.getSubcategoriesByCategoryId(categoryId));
    }
}
