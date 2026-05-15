package com.bidweather.backend_core.repository.projection;

import java.time.LocalDate;

public interface DailyCountProjection {
    LocalDate getPeriod();
    Integer getCount();
}
