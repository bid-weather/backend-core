package com.bidweather.backend_core.infra.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnouncementClassificationKafkaProducer {
    private static final String TOPIC = "announcement-classification-request";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void requestClassification(LocalDate yesterday) {
        kafkaTemplate.send(TOPIC, yesterday + "");
        log.info("용역/소분류 분류 요청 전송 완료");
    }
}
