package com.bidweather.backend_core.infra.external.init;

import com.bidweather.backend_core.infra.external.service.AnnouncementBackfillService;
import com.bidweather.backend_core.infra.kafka.AnnouncementClassificationKafkaProducer;
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
@Order(3)
@RequiredArgsConstructor
public class AnnouncementDataSeedRunner implements ApplicationRunner {

    private final AnnouncementBackfillService announcementBackfillService;
    private final AnnouncementClassificationKafkaProducer announcementClassificationKafkaProducer;

    @Override
    public void run(ApplicationArguments args) {
        log.info("========== [Seed Runner] 입찰공고 초기화 시작 ==========");
        try {
            LocalDate yesterday = LocalDate.now().minusDays(1);

            for (int year = 2024; year <= yesterday.getYear(); year++) {
                for (int month = 1; month <= 12; month++) {
                    YearMonth ym = YearMonth.of(year, month);
                    if (ym.isAfter(YearMonth.from(yesterday))) break;

                    LocalDate start = ym.atDay(1);
                    LocalDate end = ym.atEndOfMonth().isAfter(yesterday) ? yesterday : ym.atEndOfMonth();

                    announcementBackfillService.backfillForPeriod(start, end);
                    log.info("입찰공고 백필 완료: {}-{}", year, month);
                }
            }

            announcementClassificationKafkaProducer.requestClassification();
            log.info("========== [Seed Runner] 입찰공고 초기화 완료 ==========");

        } catch (Exception e) {
            log.error("입찰공고 초기화 중 에러 발생: {}", e.getMessage(), e);
        }
    }
}