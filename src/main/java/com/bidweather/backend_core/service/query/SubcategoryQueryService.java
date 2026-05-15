package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.dto.response.SubcategoryListResponseDto;
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

    public SubcategoryListResponseDto getAllSubcategories() {
        List<SubcategoryListResponseDto.SubcategoryResponseDto> subcategories = subcategoryRepository.findAll()
                .stream()
                .map(SubcategoryListResponseDto.SubcategoryResponseDto::from)
                .toList();
        return new SubcategoryListResponseDto(subcategories);
    }
}
