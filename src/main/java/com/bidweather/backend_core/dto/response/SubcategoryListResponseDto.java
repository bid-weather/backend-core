package com.bidweather.backend_core.dto.response;

import com.bidweather.backend_core.domain.Subcategory;

import java.util.List;

public record SubcategoryListResponseDto(
        List<SubcategoryResponseDto> subcategories
) {
    public record SubcategoryResponseDto(
            Long id,
            String subcategoryName
    ) {
        public static SubcategoryResponseDto from(Subcategory subcategory) {
            return new SubcategoryResponseDto(subcategory.getId(), subcategory.getSubcategoryName());
        }
    }
}
