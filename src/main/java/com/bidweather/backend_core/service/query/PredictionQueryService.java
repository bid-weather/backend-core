package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.domain.PredictionDaily;
import com.bidweather.backend_core.dto.response.GraphDataResponseDto;
import com.bidweather.backend_core.dto.response.PredictionMonthlyResponseDto;
import com.bidweather.backend_core.repository.AnnouncementRepository;
import com.bidweather.backend_core.repository.PredictionDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PredictionQueryService {

    private final PredictionDailyRepository predictionDailyRepository;
    private final AnnouncementRepository announcementRepository;

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

    public GraphDataResponseDto getGraphData(Long categoryId, Long subcategoryId) {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);

        List<GraphDataResponseDto.GraphDetailDto> graphData = new ArrayList<>();

        for (int i = -12; i <= 0; i++) {
            YearMonth targetMonth = currentMonth.plusMonths(i);
            String period = targetMonth.toString();

            LocalDate startOfMonth = targetMonth.atDay(1);
            LocalDate endOfMonth = targetMonth.atEndOfMonth();

            Integer actualCount;
            if (subcategoryId != null) {
                actualCount = announcementRepository.countBySubcategoryAndDateRange(subcategoryId, startOfMonth, endOfMonth);
            } else if (categoryId != null) {
                actualCount = announcementRepository.countByCategoryAndDateRange(categoryId, startOfMonth, endOfMonth);
            } else {
                actualCount = announcementRepository.countByDateRange(startOfMonth, endOfMonth);
            }
            actualCount = (actualCount != null && actualCount > 0) ? actualCount : null;

            List<PredictionDaily> predictionResults;
            if (subcategoryId != null) {
                predictionResults = predictionDailyRepository.findLatestBySubcategoryAndDateRange(subcategoryId, startOfMonth, endOfMonth);
            } else if (categoryId != null) {
                predictionResults = predictionDailyRepository.findLatestByCategoryAndDateRange(categoryId, startOfMonth, endOfMonth);
            } else {
                predictionResults = predictionDailyRepository.findLatestByDateRange(startOfMonth, endOfMonth);
            }

            Integer predictCount = predictionResults.isEmpty() ? null :
                    predictionResults.stream().mapToInt(PredictionDaily::getCount).sum();

            graphData.add(
                    new GraphDataResponseDto.GraphDetailDto(period, actualCount, predictCount)
            );
        }

        return new GraphDataResponseDto(graphData);
    }
}