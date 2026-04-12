package com.bidweather.backend_core.dto.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record TimeResponseDto(
        @NotNull
        LocalDate date,

        @NotNull
        String time
) {
    public static TimeResponseDto fromEntity(LocalDate date, LocalTime time) {
        return new TimeResponseDto(date, time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
