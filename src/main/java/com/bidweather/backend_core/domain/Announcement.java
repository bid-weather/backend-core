package com.bidweather.backend_core.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement")
@Getter
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "announcement_no", length = 50, nullable = false, unique = true)
    private String announcementNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date", referencedColumnName = "date", nullable = false)
    private CalendarDate calendarDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 200)
    private String issuer;

    @Column(length = 200)
    private String demander;

    @Builder
    public Announcement(CalendarDate calendarDate, String announcementNo, String title,
                        Category category, Subcategory subcategory, String issuer, String demander) {
        this.calendarDate = calendarDate;
        this.announcementNo = announcementNo;
        this.title = title;
        this.category = category;
        this.subcategory = subcategory;
        this.issuer = issuer;
        this.demander = demander;
    }
}