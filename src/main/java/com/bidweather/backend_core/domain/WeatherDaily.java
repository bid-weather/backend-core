package com.bidweather.backend_core.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "weather_daily")
@Getter
@NoArgsConstructor
public class WeatherDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date", referencedColumnName = "date", nullable = false)
    private CalendarDate calendarDate;

    @Column(name = "weather_type", length = 10, nullable = false)
    private String weatherType;

    @Column(name = "max_temp", precision = 5, scale = 2)
    private BigDecimal maxTemp;

    @Column(name = "min_temp", precision = 5, scale = 2)
    private BigDecimal minTemp;

    @Column(precision = 6, scale = 2)
    private BigDecimal precipitation;

    @Column(precision = 6, scale = 2)
    private BigDecimal snow;

    @Column(precision = 5, scale = 2)
    private BigDecimal humidity;

    @Column(name = "daily_max_wind_speed", precision = 5, scale = 2)
    private BigDecimal dailyMaxWindSpeed;

    @Builder
    public WeatherDaily(CalendarDate calendarDate, String weatherType, BigDecimal maxTemp,
                        BigDecimal minTemp, BigDecimal precipitation, BigDecimal snow,
                        BigDecimal humidity, BigDecimal dailyMaxWindSpeed) {
        this.calendarDate = calendarDate;
        this.weatherType = weatherType;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.precipitation = (precipitation != null) ? precipitation : BigDecimal.ZERO;
        this.snow = (snow != null) ? snow : BigDecimal.ZERO;
        this.humidity = humidity;
        this.dailyMaxWindSpeed = dailyMaxWindSpeed;
    }
}