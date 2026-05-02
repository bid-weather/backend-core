package com.bidweather.backend_core.infra.external.service;

import com.bidweather.backend_core.domain.WeatherDaily;
import com.bidweather.backend_core.infra.external.config.ExternalApiProperties;
import com.bidweather.backend_core.infra.external.dto.WeatherKmaResponseDto;
import com.bidweather.backend_core.repository.CalendarDateRepository;
import com.bidweather.backend_core.repository.WeatherDailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class WeatherDailyBackfillService {
    private final WeatherDailyRepository weatherDailyRepository;
    private final CalendarDateRepository calendarDateRepository;
    private final WebClient kmaWebClient;
    private final ExternalApiProperties externalApiProperties;

    public WeatherDailyBackfillService(
            WeatherDailyRepository weatherDailyRepository,
            CalendarDateRepository calendarDateRepository,
            @Qualifier("kmaWebClient") WebClient kmaWebClient,
            ExternalApiProperties externalApiProperties) {
        this.weatherDailyRepository = weatherDailyRepository;
        this.calendarDateRepository = calendarDateRepository;
        this.kmaWebClient = kmaWebClient;
        this.externalApiProperties = externalApiProperties;
    }

    @Transactional
    public void weatherBackfillForMonth(int year, int month, LocalDate yesterday) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth().isAfter(yesterday) ? yesterday : ym.atEndOfMonth();

        String response = kmaWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/typ01/url/kma_sfcdd3.php")
                        .queryParam("tm1", start.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .queryParam("tm2", end.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .queryParam("stn", 108)  // 서울
                        .queryParam("authKey", externalApiProperties.kmaKey())
                        .build(false))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response == null || response.isBlank()) return;

        List<WeatherKmaResponseDto> weathers = Arrays.stream(response.split("\n"))
                .filter(line -> !line.startsWith("#") && !line.isBlank())
                .map(WeatherKmaResponseDto::parse)
                .toList();

        createWeather(weathers);
    }

    private void createWeather(List<WeatherKmaResponseDto> weatherList) {
        List<WeatherDaily> weathers = weatherList.stream()
                .map(dto -> calendarDateRepository.findById(dto.date())
                        .map(calendarDate -> WeatherDaily.builder()
                                .calendarDate(calendarDate)
                                .weatherType(dto.deriveWeatherType())
                                .maxTemp(dto.maxTemp())
                                .minTemp(dto.minTemp())
                                .precipitation(dto.precipitation())
                                .snow(dto.maxSnow())
                                .humidity(dto.avgHumidity())
                                .dailyMaxWindSpeed(dto.maxWindSpeed())
                                .build())
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();

        weatherDailyRepository.saveAll(weathers);
    }
}
