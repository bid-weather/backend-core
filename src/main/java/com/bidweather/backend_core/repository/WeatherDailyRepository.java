package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.WeatherDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherDailyRepository extends JpaRepository<WeatherDaily, Long> {
    List<WeatherDaily> findTop7ByCalendarDate_DateLessThanEqualOrderByCalendarDate_DateDesc(LocalDate date);
}
