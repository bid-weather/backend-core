package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.PredictionDaily;
import com.bidweather.backend_core.repository.projection.DailyCountProjection;
import com.bidweather.backend_core.repository.projection.MonthlyCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PredictionDailyRepository extends JpaRepository<PredictionDaily, Long> {

    @Query("""
            SELECT TO_CHAR(p.calendarDate.date, 'YYYY-MM') AS period,
                CAST(SUM(p.count) AS integer) AS count
            FROM PredictionDaily p
            WHERE p.calendarDate.date BETWEEN :start AND :end
            AND p.predictedAt = (
                SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
                WHERE p2.calendarDate.date = p.calendarDate.date
                AND p2.category = p.category
                AND p2.subcategory = p.subcategory
            )
            GROUP BY TO_CHAR(p.calendarDate.date, 'YYYY-MM')
            """)
    List<MonthlyCountProjection> findLatestByDateRange(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
            SELECT TO_CHAR(p.calendarDate.date, 'YYYY-MM') AS period,
                CAST(SUM(p.count) AS integer) AS count
            FROM PredictionDaily p
            WHERE p.calendarDate.date BETWEEN :start AND :end
            AND p.category.id = :categoryId
            AND p.predictedAt = (
                SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
                WHERE p2.calendarDate.date = p.calendarDate.date
                AND p2.category = p.category
                AND p2.subcategory = p.subcategory
            )
            GROUP BY TO_CHAR(p.calendarDate.date, 'YYYY-MM')
            """)
    List<MonthlyCountProjection> findLatestByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
            SELECT TO_CHAR(p.calendarDate.date, 'YYYY-MM') AS period,
                CAST(SUM(p.count) AS integer) AS count
            FROM PredictionDaily p
            WHERE p.calendarDate.date BETWEEN :start AND :end
            AND p.category.id = :categoryId
            AND p.subcategory.id = :subcategoryId
            AND p.predictedAt = (
                SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
                WHERE p2.calendarDate.date = p.calendarDate.date
                AND p2.category = p.category
                AND p2.subcategory = p.subcategory
            )
            GROUP BY TO_CHAR(p.calendarDate.date, 'YYYY-MM')
            """)
    List<MonthlyCountProjection> findLatestByCategoryAndSubcategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("subcategoryId") Long subcategoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
        SELECT TO_CHAR(p.calendarDate.date, 'YYYY-MM-DD') AS period,
                CAST(SUM(p.count) AS integer) AS count
        FROM PredictionDaily p
        WHERE p.calendarDate.date BETWEEN :start AND :end
        AND p.predictedAt = (
            SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
            WHERE p2.calendarDate.date = p.calendarDate.date
            AND p2.category = p.category
            AND p2.subcategory = p.subcategory
        )
        GROUP BY p.calendarDate.date
        """)
    List<DailyCountProjection> findLatestDailyByDateRange(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
        SELECT TO_CHAR(p.calendarDate.date, 'YYYY-MM-DD') AS period,
                CAST(SUM(p.count) AS integer) AS count
        FROM PredictionDaily p
        WHERE p.calendarDate.date BETWEEN :start AND :end
        AND p.category.id = :categoryId
        AND p.predictedAt = (
            SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
            WHERE p2.calendarDate.date = p.calendarDate.date
            AND p2.category = p.category
            AND p2.subcategory = p.subcategory
        )
        GROUP BY p.calendarDate.date
        """)
    List<DailyCountProjection> findLatestDailyByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
        SELECT TO_CHAR(p.calendarDate.date, 'YYYY-MM-DD') AS period,
                CAST(SUM(p.count) AS integer) AS count
        FROM PredictionDaily p
        WHERE p.calendarDate.date BETWEEN :start AND :end
        AND p.category.id = :categoryId
        AND p.subcategory.id = :subcategoryId
        AND p.predictedAt = (
            SELECT MAX(p2.predictedAt) FROM PredictionDaily p2
            WHERE p2.calendarDate.date = p.calendarDate.date
            AND p2.category = p.category
            AND p2.subcategory = p.subcategory
        )
        GROUP BY p.calendarDate.date
        """)
    List<DailyCountProjection> findLatestDailyByCategoryAndSubcategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("subcategoryId") Long subcategoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}