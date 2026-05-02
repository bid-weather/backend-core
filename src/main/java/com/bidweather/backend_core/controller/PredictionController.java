package com.bidweather.backend_core.controller;

import com.bidweather.backend_core.dto.response.PredictionMonthlyResponseDto;
import com.bidweather.backend_core.service.query.PredictionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionQueryService predictionQueryService;

    @GetMapping("/calendar")
    public ResponseEntity<PredictionMonthlyResponseDto> getMonthlyPredictions(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long subcategoryId) {
        return ResponseEntity.ok(predictionQueryService.findMonthlyPredictions(categoryId, subcategoryId));
    }
}