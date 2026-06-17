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

    @Query(value = """
            SELECT TO_CHAR(date, 'YYYY-MM') AS period, CAST(SUM(count) AS integer) AS count
            FROM (
                SELECT date, count,
                       ROW_NUMBER() OVER (PARTITION BY date, category_id, subcategory_id ORDER BY predicted_at DESC) AS rn
                FROM prediction_daily
                WHERE date BETWEEN :start AND :end
            ) ranked
            WHERE rn = 1
            GROUP BY TO_CHAR(date, 'YYYY-MM')
            """, nativeQuery = true)
    List<MonthlyCountProjection> findLatestByDateRange(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query(value = """
            SELECT TO_CHAR(date, 'YYYY-MM') AS period, CAST(SUM(count) AS integer) AS count
            FROM (
                SELECT date, count,
                       ROW_NUMBER() OVER (PARTITION BY date, category_id, subcategory_id ORDER BY predicted_at DESC) AS rn
                FROM prediction_daily
                WHERE date BETWEEN :start AND :end
                AND category_id = :categoryId
            ) ranked
            WHERE rn = 1
            GROUP BY TO_CHAR(date, 'YYYY-MM')
            """, nativeQuery = true)
    List<MonthlyCountProjection> findLatestByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query(value = """
            SELECT TO_CHAR(date, 'YYYY-MM') AS period, CAST(SUM(count) AS integer) AS count
            FROM (
                SELECT date, count,
                       ROW_NUMBER() OVER (PARTITION BY date, category_id, subcategory_id ORDER BY predicted_at DESC) AS rn
                FROM prediction_daily
                WHERE date BETWEEN :start AND :end
                AND category_id = :categoryId
                AND subcategory_id = :subcategoryId
            ) ranked
            WHERE rn = 1
            GROUP BY TO_CHAR(date, 'YYYY-MM')
            """, nativeQuery = true)
    List<MonthlyCountProjection> findLatestByCategoryAndSubcategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("subcategoryId") Long subcategoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query(value = """
            SELECT date AS period, CAST(SUM(count) AS integer) AS count
            FROM (
                SELECT date, count,
                       ROW_NUMBER() OVER (PARTITION BY date, category_id, subcategory_id ORDER BY predicted_at DESC) AS rn
                FROM prediction_daily
                WHERE date BETWEEN :start AND :end
            ) ranked
            WHERE rn = 1
            GROUP BY date
            """, nativeQuery = true)
    List<DailyCountProjection> findLatestDailyByDateRange(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query(value = """
            SELECT date AS period, CAST(SUM(count) AS integer) AS count
            FROM (
                SELECT date, count,
                       ROW_NUMBER() OVER (PARTITION BY date, category_id, subcategory_id ORDER BY predicted_at DESC) AS rn
                FROM prediction_daily
                WHERE date BETWEEN :start AND :end
                AND category_id = :categoryId
            ) ranked
            WHERE rn = 1
            GROUP BY date
            """, nativeQuery = true)
    List<DailyCountProjection> findLatestDailyByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query(value = """
            SELECT date AS period, CAST(SUM(count) AS integer) AS count
            FROM (
                SELECT date, count,
                       ROW_NUMBER() OVER (PARTITION BY date, category_id, subcategory_id ORDER BY predicted_at DESC) AS rn
                FROM prediction_daily
                WHERE date BETWEEN :start AND :end
                AND category_id = :categoryId
                AND subcategory_id = :subcategoryId
            ) ranked
            WHERE rn = 1
            GROUP BY date
            """, nativeQuery = true)
    List<DailyCountProjection> findLatestDailyByCategoryAndSubcategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("subcategoryId") Long subcategoryId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}