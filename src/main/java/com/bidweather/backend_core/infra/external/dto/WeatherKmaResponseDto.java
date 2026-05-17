package com.bidweather.backend_core.infra.external.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record WeatherKmaResponseDto(
        LocalDate date,
        BigDecimal maxWindSpeed,    // WS_MAX (6)
        BigDecimal maxTemp,         // TA_MAX (12)
        BigDecimal minTemp,         // TA_MIN (14)
        BigDecimal avgHumidity,     // HM_AVG (19)
        BigDecimal precipitation,   // RN_DAY (39)
        BigDecimal maxSnow,         // SD_MAX (50)
        BigDecimal sunshine,        // SS_DAY (33)
        BigDecimal cloudiness       // CA_TOT (32)
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String MISSING = "-9";
    private static final String MISSING2 = "-9.0";
    private static final String MISSING3 = "-9.00";

    public static WeatherKmaResponseDto parse(String line) {
        String[] tokens = line.trim().split("\\s+");

        return new WeatherKmaResponseDto(
                LocalDate.parse(tokens[0], FORMATTER),
                parseBigDecimal(tokens[5]),
                parseBigDecimal(tokens[11]),
                parseBigDecimal(tokens[13]),
                parseBigDecimal(tokens[18]),
                parseBigDecimal(tokens[38]),
                parseBigDecimal(tokens[49]),
                parseBigDecimal(tokens[32]),
                parseBigDecimal(tokens[31])
        );
    }

    public String deriveWeatherType() {
        if (isPositive(maxSnow)) return "SNOW";
        if (isPositive(precipitation)) return "RAIN";
        if (maxWindSpeed != null && maxWindSpeed.compareTo(new BigDecimal("14")) >= 0) return "WINDY";
        if (cloudiness != null && cloudiness.compareTo(new BigDecimal("8")) >= 0) return "CLOUDY";
        if (isPositive(sunshine)) return "SUNNY";
        return "CLOUDY";
    }

    private static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    private static BigDecimal parseBigDecimal(String value) {
        if (value == null || value.equals(MISSING) || value.equals(MISSING2) || value.equals(MISSING3)) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
