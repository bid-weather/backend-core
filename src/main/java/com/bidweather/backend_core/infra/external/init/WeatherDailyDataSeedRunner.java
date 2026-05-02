package com.bidweather.backend_core.infra.external.init;

import com.bidweather.backend_core.infra.external.service.WeatherDailyBackfillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class WeatherDailyDataSeedRunner implements ApplicationRunner {
    private final WeatherDailyBackfillService weatherDailyBackfillService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("========== [Seed Runner] 날씨 초기화 시작 ==========");
        try {
            LocalDate yesterday = LocalDate.now().minusDays(1);

            for (int targetYear = 2024; targetYear <= yesterday.getYear(); targetYear++) {
                for (int targetMonth = 1; targetMonth <= 12; targetMonth++) {
                    if (YearMonth.of(targetYear, targetMonth).isAfter(YearMonth.from(yesterday))) break;
                    weatherDailyBackfillService.weatherBackfillForMonth(targetYear, targetMonth, yesterday);
                }
            }
            log.info("========== [Seed Runner] 날씨 초기화 완료 ==========");

        } catch (Exception e) {
            log.error("날씨 초기화 중 에러 발생: {}", e.getMessage(), e);
        }
    }
}
