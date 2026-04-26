package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.domain.Subcategory;
import com.bidweather.backend_core.dto.response.SubcategoryResponseDto;
import com.bidweather.backend_core.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubcategoryQueryService {
    private final SubcategoryRepository subcategoryRepository;

    public SubcategoryResponseDto<String> getAllSubcategories() {
        List<String> subcategoryNames = subcategoryRepository.findAll().stream()
                .map(Subcategory::getSubcategoryName)
                .toList();
        return new SubcategoryResponseDto<>(subcategoryNames);
    }
}
