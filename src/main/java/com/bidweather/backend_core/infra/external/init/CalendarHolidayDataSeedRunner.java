package com.bidweather.backend_core.infra.external.init;

import com.bidweather.backend_core.infra.external.service.CalendarHolidayBackfillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class CalendarHolidayDataSeedRunner implements ApplicationRunner {

    private final CalendarHolidayBackfillService calendarHolidayBackfillService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("========== [Seed Runner] 공휴일 초기화 시작 ==========");
        try {
            for(int targetYear = 2024; targetYear <= 2026; targetYear++) {
                for (int month = 1; month <= 12; month++) {
                    calendarHolidayBackfillService.holidayBackfillForMonth(targetYear, month);
                }
            }
            log.info("========== [Seed Runner] 공휴일 초기화 완료 ==========");

        } catch (Exception e) {
            log.error("공휴일 초기화 중 에러 발생: {}", e.getMessage(), e);
        }
    }
}
