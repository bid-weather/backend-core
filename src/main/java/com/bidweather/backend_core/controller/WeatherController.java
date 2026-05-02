package com.bidweather.backend_core.controller;

import com.bidweather.backend_core.dto.response.WeatherResponseDto;
import com.bidweather.backend_core.service.query.WeatherQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherQueryService weatherQueryService;

    @GetMapping
    public ResponseEntity<Map<String, WeatherResponseDto>> getWeathers() {
        return ResponseEntity.ok(weatherQueryService.getWeeklyWeatherUpToYesterday());
    }
}
