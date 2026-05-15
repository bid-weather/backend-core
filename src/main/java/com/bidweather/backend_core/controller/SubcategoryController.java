package com.bidweather.backend_core.controller;

import com.bidweather.backend_core.dto.response.SubcategoryListResponseDto;
import com.bidweather.backend_core.service.query.SubcategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subcategories")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryQueryService subcategoryQueryService;

    @GetMapping("")
    public ResponseEntity<SubcategoryListResponseDto> getSubcategories() {
        return ResponseEntity.ok(subcategoryQueryService.getAllSubcategories());
    }
}
