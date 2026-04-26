package com.bidweather.backend_core.dto.response;

import java.util.List;

public record SubcategoryResponseDto<T>(
        List<T> subcategories
) {
}
