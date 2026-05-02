package com.bidweather.backend_core.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prediction_daily")
@NoArgsConstructor
@Getter
public class PredictionDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date", referencedColumnName = "date", nullable = false)
    private CalendarDate calendarDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    @Column(nullable = false)
    private Integer count;

    @Column(name = "predicted_at", nullable = false)
    private LocalDate predictedAt;

    @Builder
    public PredictionDaily(CalendarDate calendarDate, Category category,
                           Subcategory subcategory, Integer count, LocalDate predictedAt) {
        this.calendarDate = calendarDate;
        this.category = category;
        this.subcategory = subcategory;
        this.count = count;
        this.predictedAt = predictedAt;
    }
}
