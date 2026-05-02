package com.bidweather.backend_core.infra.kafka;

import com.bidweather.backend_core.infra.sse.SseEmitterRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PredictionResultKafkaConsumer {

    private final SseEmitterRegistry sseEmitterRegistry;

    @KafkaListener(topics = "announcement-prediction-result", groupId = "prediction-group")
    public void consume(String message) {
        log.info("========== [Kafka] 예측 완료 콜백 수신 : {} ==========", message);
        sseEmitterRegistry.broadcast("prediction-updated", message);
    }
}
