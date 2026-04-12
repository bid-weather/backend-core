package com.bidweather.backend_core.service.query;

import com.bidweather.backend_core.dto.response.TimeResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional
public class SystemQueryService {
    public TimeResponseDto getTime() {
        return TimeResponseDto.fromEntity(LocalDate.now(), LocalTime.now());
    }
}
