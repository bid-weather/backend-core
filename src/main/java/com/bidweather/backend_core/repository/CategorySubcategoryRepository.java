package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.CategorySubcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorySubcategoryRepository extends JpaRepository<CategorySubcategory, Long> {
    List<CategorySubcategory> findByCategory_Id(Long categoryId);
}
