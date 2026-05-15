package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.CategorySubcategory;
import com.bidweather.backend_core.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategorySubcategoryRepository extends JpaRepository<CategorySubcategory, Long> {

    @Query("""
        SELECT cs.subcategory
        FROM CategorySubcategory cs
        WHERE cs.category.id = :categoryId
    """)
    List<Subcategory> findSubcategoriesByCategoryId(Long categoryId);
}
