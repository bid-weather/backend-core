package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByAnnouncementNoIn(Collection<String> announcementNos);

    @Query("SELECT COUNT(a) FROM Announcement a WHERE a.calendarDate.date BETWEEN :start AND :end")
    Integer countByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COUNT(a) FROM Announcement a WHERE a.category.id = :categoryId AND a.calendarDate.date BETWEEN :start AND :end")
    Integer countByCategoryAndDateRange(@Param("categoryId") Long categoryId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COUNT(a) FROM Announcement a WHERE a.subcategory.id = :subcategoryId AND a.calendarDate.date BETWEEN :start AND :end")
    Integer countBySubcategoryAndDateRange(@Param("subcategoryId") Long subcategoryId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
