package com.bidweather.backend_core.dto.response;

import java.util.List;

public record GraphDataResponseDto(
        List<GraphDetailDto> graphData
) {
    public record GraphDetailDto(
            String period,
            Integer actualCount,
            Integer predictCount
    ) {}
}
