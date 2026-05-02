package com.bidweather.backend_core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calendar_date")
@NoArgsConstructor
@Getter
public class CalendarDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private LocalDate date;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer day;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "is_weekend", nullable = false)
    private Boolean isWeekend;

    @Column(name = "is_holiday")
    private Boolean isHoliday;

    @Column(name = "day_of_year", nullable = false)
    private Integer dayOfYear;

    @OneToMany(mappedBy = "calendarDate", fetch = FetchType.LAZY)
    private List<WeatherDaily> weatherDailies = new ArrayList<>();

    public void markAsHoliday() {
        this.isHoliday = true;
    }

    public void markAsWeekday() {
        this.isHoliday = false;
    }
}
