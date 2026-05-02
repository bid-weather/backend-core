package com.bidweather.backend_core.dto.response;

import java.time.LocalDate;
import java.util.List;

public record PredictionMonthlyResponseDto(
    List<PredictionDailyResponseDto> predictions
) {
    public record PredictionDailyResponseDto(
            LocalDate date,
            Integer count
    ) {}
}
