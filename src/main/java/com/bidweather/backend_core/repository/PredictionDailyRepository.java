package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.PredictionDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PredictionDailyRepository extends JpaRepository<PredictionDaily, Long> {

    @Query("""
            SELECT p FROM PredictionDaily p
            WHERE p.calendarDate.date BETWEEN :start AND :end
            AND p.predictedAt = (
                SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
                WHERE p2.calendarDate.date = p.calendarDate.date
                AND p2.subcategory = p.subcategory
            )
            """)
    List<PredictionDaily> findLatestByDateRange(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
            SELECT p FROM PredictionDaily p
            WHERE p.calendarDate.date BETWEEN :start AND :end
            AND p.category.id = :categoryId
            AND p.predictedAt = (
                SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
                WHERE p2.calendarDate.date = p.calendarDate.date
                AND p2.subcategory = p.subcategory
            )
            """)
    List<PredictionDaily> findLatestByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
            SELECT p FROM PredictionDaily p
            WHERE p.calendarDate.date BETWEEN :start AND :end
            AND p.subcategory.id = :subcategoryId
            AND p.predictedAt = (
                SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
                WHERE p2.calendarDate.date = p.calendarDate.date
                AND p2.subcategory = p.subcategory
            )
            """)
    List<PredictionDaily> findLatestBySubcategoryAndDateRange(
            @Param("subcategoryId") Long subcategoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
