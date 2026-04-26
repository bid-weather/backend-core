package com.bidweather.backend_core.dto.response;

import java.util.List;

public record CategoryResponseDto<T>(
        List<T> categories
) {
}
