package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.dto.response.CategoryListResponseDto;
import com.bidweather.backend_core.dto.response.SubcategoryListResponseDto;
import com.bidweather.backend_core.repository.CategoryRepository;
import com.bidweather.backend_core.repository.CategorySubcategoryRepository;
import com.bidweather.backend_core.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryQueryService {
    private final CategoryRepository categoryRepository;
    private final CategorySubcategoryRepository categorySubcategoryRepository;

    public CategoryListResponseDto getAllCategories() {
        List<CategoryListResponseDto.CategoryResponseDto> categories = categoryRepository.findAll()
                .stream()
                .map(CategoryListResponseDto.CategoryResponseDto::from)
                .toList();
        return new CategoryListResponseDto(categories);
    }

    public SubcategoryListResponseDto getSubcategoriesByCategoryId(Long categoryId) {
        List<SubcategoryListResponseDto.SubcategoryResponseDto> subcategories = categorySubcategoryRepository.findSubcategoriesByCategoryId(categoryId)
                .stream()
                .map(SubcategoryListResponseDto.SubcategoryResponseDto::from)
                .toList();
        return new SubcategoryListResponseDto(subcategories);
    }
}
