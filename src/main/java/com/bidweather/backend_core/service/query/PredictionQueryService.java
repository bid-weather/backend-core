package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.dto.response.GraphDataResponseDto;
import com.bidweather.backend_core.dto.response.PredictionMonthlyResponseDto;
import com.bidweather.backend_core.repository.AnnouncementRepository;
import com.bidweather.backend_core.repository.PredictionDailyRepository;
import com.bidweather.backend_core.repository.projection.DailyCountProjection;
import com.bidweather.backend_core.repository.projection.MonthlyCountProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
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

        List<DailyCountProjection> results;
        if (subcategoryId != null) {
            results = predictionDailyRepository.findLatestDailyBySubcategoryAndDateRange(subcategoryId, start, end);
        } else if (categoryId != null) {
            results = predictionDailyRepository.findLatestDailyByCategoryAndDateRange(categoryId, start, end);
        } else {
            results = predictionDailyRepository.findLatestDailyByDateRange(start, end);
        }

        List<PredictionMonthlyResponseDto.PredictionDailyResponseDto> predictions = results.stream()
                .sorted(Comparator.comparing(DailyCountProjection::getPeriod))
                .map(p -> new PredictionMonthlyResponseDto.PredictionDailyResponseDto(p.getPeriod(), p.getCount()))
                .toList();

        return new PredictionMonthlyResponseDto(predictions);
    }

    public GraphDataResponseDto getGraphData(Long categoryId, Long subcategoryId) {
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        LocalDate start = currentMonth.minusMonths(12).atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        List<MonthlyCountProjection> actualResults;
        if (subcategoryId != null) {
            actualResults = announcementRepository.countBySubcategoryAndDateRange(subcategoryId, start, end);
        } else if (categoryId != null) {
            actualResults = announcementRepository.countByCategoryAndDateRange(categoryId, start, end);
        } else {
            actualResults = announcementRepository.countByDateRange(start, end);
        }

        List<MonthlyCountProjection> predictResults;
        if (subcategoryId != null) {
            predictResults = predictionDailyRepository.findLatestBySubcategoryAndDateRange(subcategoryId, start, end);
        } else if (categoryId != null) {
            predictResults = predictionDailyRepository.findLatestByCategoryAndDateRange(categoryId, start, end);
        } else {
            predictResults = predictionDailyRepository.findLatestByDateRange(start, end);
        }

        Map<String, Integer> actualMap = actualResults.stream()
                .collect(Collectors.toMap(MonthlyCountProjection::getPeriod, MonthlyCountProjection::getCount));
        Map<String, Integer> predictMap = predictResults.stream()
                .collect(Collectors.toMap(MonthlyCountProjection::getPeriod, MonthlyCountProjection::getCount));

        List<GraphDataResponseDto.GraphDetailDto> graphData = new ArrayList<>();
        for (int i = -12; i <= 0; i++) {
            String period = currentMonth.plusMonths(i).toString();
            Integer actualCount = actualMap.getOrDefault(period, null);
            Integer predictCount = predictMap.getOrDefault(period, null);
            graphData.add(new GraphDataResponseDto.GraphDetailDto(period, actualCount, predictCount));
        }

        return new GraphDataResponseDto(graphData);
    }
}