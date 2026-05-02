package com.bidweather.backend_core.infra.external.service;


import com.bidweather.backend_core.domain.Announcement;
import com.bidweather.backend_core.domain.Category;
import com.bidweather.backend_core.infra.external.config.ExternalApiProperties;
import com.bidweather.backend_core.infra.external.dto.AnnouncementG2bResponseDto;
import com.bidweather.backend_core.repository.AnnouncementRepository;
import com.bidweather.backend_core.repository.CalendarDateRepository;
import com.bidweather.backend_core.repository.CategoryRepository;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnnouncementBackfillService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;
    private static final int PAGE_SIZE = 100;

    private final AnnouncementRepository announcementRepository;
    private final CalendarDateRepository calendarDateRepository;
    private final CategoryRepository categoryRepository;
    private final WebClient g2bWebClient;
    private final ExternalApiProperties externalApiProperties;

    public AnnouncementBackfillService(
            AnnouncementRepository announcementRepository,
            CalendarDateRepository calendarDateRepository,
            CategoryRepository categoryRepository,
            @Qualifier("g2bWebClient") WebClient g2bWebClient,
            ExternalApiProperties externalApiProperties) {
        this.announcementRepository = announcementRepository;
        this.calendarDateRepository = calendarDateRepository;
        this.categoryRepository = categoryRepository;
        this.g2bWebClient = g2bWebClient;
        this.externalApiProperties = externalApiProperties;
    }

    public void backfillForDate(LocalDate date) {
        for (BidApiType bidApiType : BidApiType.values()) {
            fetchAndSave(bidApiType, date, date);
        }
    }

    public void backfillForPeriod(LocalDate start, LocalDate end) {
        for (BidApiType bidApiType : BidApiType.values()) {
            fetchAndSave(bidApiType, start, end);
        }
    }

    private void fetchAndSave(BidApiType bidApiType, LocalDate start, LocalDate end) {
        int pageNo = 1;
        int totalCount;

        do {
            AnnouncementG2bResponseDto response = fetchPage(bidApiType, start, end, pageNo);

            if (response == null || response.response().body() == null) {
                log.debug("입찰공고 응답 없음: type={}, date={}", bidApiType, start);
                return;
            }

            List<AnnouncementG2bResponseDto.Item> items = response.response().body().items();
            totalCount = response.response().body().totalCount();

            if (items != null && !items.isEmpty()) {
                createAnnouncements(items, bidApiType);
            }

            pageNo++;
        } while ((pageNo - 1) * PAGE_SIZE < totalCount);
    }

    private AnnouncementG2bResponseDto fetchPage(BidApiType bidApiType, LocalDate start, LocalDate end, int pageNo) {
        return g2bWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(bidApiType.getPath())
                        .queryParam("serviceKey", externalApiProperties.g2bKey())
                        .queryParam("inqryDiv", 1)
                        .queryParam("inqryBgnDt", start.format(DATE_FORMAT) + "0000")
                        .queryParam("inqryEndDt", end.format(DATE_FORMAT) + "2359")
                        .queryParam("pageNo", pageNo)
                        .queryParam("numOfRows", PAGE_SIZE)
                        .queryParam("type", "json")
                        .build())
                .retrieve()
                .bodyToMono(AnnouncementG2bResponseDto.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof ReadTimeoutException || throwable instanceof WebClientRequestException)
                        .doBeforeRetry(retrySignal -> log.warn("재시도 중... 사유: {}", retrySignal.failure().getMessage())))
                .block();
    }

    @Transactional
    protected void createAnnouncements(List<AnnouncementG2bResponseDto.Item> items, BidApiType bidApiType) {
        List<AnnouncementG2bResponseDto.Item> distinctItems = items.stream()
                .collect(Collectors.toMap(
                        AnnouncementG2bResponseDto.Item::bidNtceNo,
                        item -> item,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();

        List<String> nos = distinctItems.stream().map(AnnouncementG2bResponseDto.Item::bidNtceNo).toList();

        Set<String> existingNos = announcementRepository.findAllByAnnouncementNoIn(nos).stream()
                .map(Announcement::getAnnouncementNo).collect(Collectors.toSet());

        Category category = bidApiType.getCategoryId() != null
                ? categoryRepository.findById(bidApiType.getCategoryId()).orElse(null)
                : null;

        List<Announcement> announcements = distinctItems.stream()
                .filter(item -> !existingNos.contains(item.bidNtceNo()))
                .map(item -> {
                    return calendarDateRepository.findById(LocalDate.parse(item.getAnnouncementDate()))
                            .map(calendarDate -> Announcement.builder()
                                    .announcementNo(item.bidNtceNo())
                                    .calendarDate(calendarDate)
                                    .title(item.bidNtceNm())
                                    .category(category)
                                    .issuer(item.ntceInsttNm())
                                    .demander(item.dminsttNm())
                                    .build())
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();

        if (!announcements.isEmpty()) {
            announcementRepository.saveAllAndFlush(announcements);
            log.info("입찰공고 저장: type={}, count={}", bidApiType, announcements.size());
        }
    }
}
