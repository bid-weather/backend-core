package com.bidweather.backend_core.infra.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class PredictionRequestKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "announcement-prediction";

    @Scheduled(cron = "0 0 4 * * *")
    public void requestPrediction() {
        String today = LocalDate.now().toString();
        log.info("========== [Kafka] 예측 분석 요청 전송: {} ==========", today);
        kafkaTemplate.send(TOPIC, today);
    }
}