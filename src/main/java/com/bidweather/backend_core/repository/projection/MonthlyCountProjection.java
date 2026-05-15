package com.bidweather.backend_core.repository.projection;

public interface MonthlyCountProjection {
    String getPeriod();
    Integer getCount();
}
