package com.bidweather.backend_core.dto.response;

import com.bidweather.backend_core.domain.Category;

import java.util.List;

public record CategoryListResponseDto(
        List<CategoryResponseDto> categories
) {
    public record CategoryResponseDto(
            Long id,
            String categoryName
    ) {
        public static CategoryResponseDto from(Category category) {
            return new CategoryResponseDto(category.getId(), category.getCategoryName());
        }
    }
}
