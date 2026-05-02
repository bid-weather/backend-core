package com.bidweather.backend_core.repository;

import com.bidweather.backend_core.domain.WeatherDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDailyRepository extends JpaRepository<WeatherDaily, Long> {
}
