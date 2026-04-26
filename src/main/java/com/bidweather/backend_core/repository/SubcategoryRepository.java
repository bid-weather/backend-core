package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
}
