package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.domain.WeatherDaily;
import com.bidweather.backend_core.dto.response.WeatherResponseDto;
import com.bidweather.backend_core.repository.WeatherDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class WeatherQueryService {
    private final WeatherDailyRepository weatherDailyRepository;

    public Map<String, WeatherResponseDto> getWeeklyWeatherUpToYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<WeatherDaily> weathers = weatherDailyRepository
                .findTop7ByCalendarDate_DateLessThanEqualOrderByCalendarDate_DateDesc(yesterday);

        Map<String, WeatherResponseDto> result = new LinkedHashMap<>();
        for (int i = 0; i < weathers.size(); i++) {
            String key = (i + 1) + "ago";
            result.put(key, WeatherResponseDto.from(weathers.get(i)));
        }
        return result;
    }
}
