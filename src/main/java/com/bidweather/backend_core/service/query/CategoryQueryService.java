package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.domain.Category;
import com.bidweather.backend_core.dto.response.CategoryResponseDto;
import com.bidweather.backend_core.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
