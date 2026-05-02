package com.bidweather.backend_core.infra.external.scheduler;

import com.bidweather.backend_core.infra.external.service.WeatherDailyBackfillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherDailyScheduler {

    private final WeatherDailyBackfillService weatherDailyBackfillService;

    @Scheduled(cron = "0 5 0 * * *")
    public void collectYesterdayWeather() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        log.info("========== [Scheduler] 날씨 데이터 수집 시작: {} ==========", yesterday);
        try {
            weatherDailyBackfillService.weatherBackfillForDate(yesterday);
            log.info("========== [Scheduler] 날씨 데이터 수집 완료 ==========");
        } catch (Exception e) {
            log.error("날씨 데이터 수집 중 에러 발생: {}", e.getMessage(), e);
        }
    }
}
