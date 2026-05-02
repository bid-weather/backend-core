package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.CalendarDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, LocalDate> {
    public List<CalendarDate> findByYearAndMonth(int year, int month);
}
