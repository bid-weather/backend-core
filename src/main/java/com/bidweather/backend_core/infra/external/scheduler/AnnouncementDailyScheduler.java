package com.bidweather.backend_core.infra.external.scheduler;

import com.bidweather.backend_core.infra.external.service.AnnouncementBackfillService;
import com.bidweather.backend_core.infra.kafka.AnnouncementClassificationKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnouncementDailyScheduler {
    private final AnnouncementBackfillService announcementBackfillService;
    private final AnnouncementClassificationKafkaProducer announcementClassificationKafkaProducer;

    @Scheduled(cron = "0 10 0 * * *")
    public void fetchDailyAnnouncements() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        log.info("입찰공고 스케줄러 시작: {}", yesterday);

        try {
            announcementBackfillService.backfillForDate(yesterday);
            announcementClassificationKafkaProducer.requestClassification();
            log.info("입찰공고 스케줄러 완료: {}", yesterday);

        } catch (Exception e) {
            log.error("입찰공고 스케줄러 실패: {}", e.getMessage(), e);
        }
    }
}
