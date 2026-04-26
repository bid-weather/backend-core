package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.domain.Category;
import com.bidweather.backend_core.domain.CategorySubcategory;
import com.bidweather.backend_core.domain.Subcategory;
import com.bidweather.backend_core.dto.response.CategoryResponseDto;
import com.bidweather.backend_core.dto.response.SubcategoryResponseDto;
import com.bidweather.backend_core.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponseDto<String> getAllCategories() {
        List<String> categoryNames = categoryRepository.findAll().stream()
                .map(Category::getCategoryName)
                .toList();
        return new CategoryResponseDto<>(categoryNames);
    }

    public SubcategoryResponseDto<String> getSubcategoriesByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException());

        List<String> subcategories = category.getCategorySubcategories().stream()
                .map(CategorySubcategory::getSubcategory)
                .map(Subcategory::getSubcategoryName)
                .toList();

        return new SubcategoryResponseDto<>(subcategories);
    }
}
