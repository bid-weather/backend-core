package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.domain.PredictionDaily;
import com.bidweather.backend_core.dto.response.PredictionMonthlyResponseDto;
import com.bidweather.backend_core.repository.PredictionDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PredictionQueryService {

    private final PredictionDailyRepository predictionDailyRepository;

    public PredictionMonthlyResponseDto findMonthlyPredictions(Long categoryId, Long subcategoryId) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(1).minusDays(1);

        List<PredictionDaily> results;

        if (subcategoryId != null) {
            results = predictionDailyRepository.findLatestBySubcategoryAndDateRange(subcategoryId, start, end);
        } else if (categoryId != null) {
            results = predictionDailyRepository.findLatestByCategoryAndDateRange(categoryId, start, end);
        } else {
            results = predictionDailyRepository.findLatestByDateRange(start, end);
        }

        Map<LocalDate, Integer> countByDate = results.stream()
                .collect(Collectors.groupingBy(
                        p -> ((com.bidweather.backend_core.domain.PredictionDaily) p).getCalendarDate().getDate(),
                        Collectors.summingInt(p -> ((com.bidweather.backend_core.domain.PredictionDaily) p).getCount())
                ));

        List<PredictionMonthlyResponseDto.PredictionDailyResponseDto> predictions = countByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new PredictionMonthlyResponseDto.PredictionDailyResponseDto(e.getKey(), e.getValue()))
                .toList();

        return new PredictionMonthlyResponseDto(predictions);
    }
}