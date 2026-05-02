package com.bidweather.backend_core.dto.response;

import com.bidweather.backend_core.domain.WeatherDaily;

import java.math.BigDecimal;

public record WeatherResponseDto(
        String weatherType,
        BigDecimal maxTemp,
        BigDecimal minTemp,
        BigDecimal precipitation,
        BigDecimal humidity,
        BigDecimal dailyMaxWindSpeed
) {
    public static WeatherResponseDto from(WeatherDaily weather) {
        return new WeatherResponseDto(
                weather.getWeatherType(),
                weather.getMaxTemp(),
                weather.getMinTemp(),
                weather.getPrecipitation(),
                weather.getHumidity(),
                weather.getDailyMaxWindSpeed()
        );
    }
}
