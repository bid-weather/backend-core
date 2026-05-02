package com.bidweather.backend_core.infra.external.service;

import com.bidweather.backend_core.domain.CalendarDate;
import com.bidweather.backend_core.repository.CalendarDateRepository;
import com.bidweather.backend_core.infra.external.config.ExternalApiProperties;
import com.bidweather.backend_core.infra.external.dto.HolidayXmlResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.dataformat.xml.XmlMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class CalendarHolidayBackfillService {
    private final CalendarDateRepository calendarDateRepository;
    private final WebClient holidayWebClient;
    private final ExternalApiProperties externalApiProperties;

    public CalendarHolidayBackfillService(
            CalendarDateRepository calendarDateRepository,
            @Qualifier("holidayWebClient") WebClient holidayWebClient,
            ExternalApiProperties externalApiProperties
    ) {
        this.calendarDateRepository = calendarDateRepository;
        this.holidayWebClient = holidayWebClient;
        this.externalApiProperties = externalApiProperties;
    }

    @Transactional
    public void holidayBackfillForMonth(int year, int month) {
        List<CalendarDate> dates = calendarDateRepository.findByYearAndMonth(year, month);
        if (dates.isEmpty()) {
            throw new IllegalStateException("Calendar data not found");
        }

        for(CalendarDate date : dates) {
            if(date.getIsWeekend()) {
                date.markAsHoliday();
            }
        }

        List<HolidayXmlResponseDto.HolidayItem> holidays = fetchHolidays(year, month);

        applyHolidayToCalendar(holidays);

        for(CalendarDate date : dates) {
            if(date.getIsHoliday() == null) {
                date.markAsWeekday();
            }
        }
    }

    private void applyHolidayToCalendar(List<HolidayXmlResponseDto.HolidayItem> holidays) {
        if (holidays == null || holidays.isEmpty()) return;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (HolidayXmlResponseDto.HolidayItem item : holidays) {
            LocalDate holidayDate = LocalDate.parse(item.locdate(), dateTimeFormatter);

            calendarDateRepository.findById(holidayDate)
                    .ifPresent(CalendarDate::markAsHoliday);

            log.info("공휴일 업데이트: {} ({})", holidayDate, item.dateName());
        }
    }

    private List<HolidayXmlResponseDto.HolidayItem> fetchHolidays(int year, int month) {
        XmlMapper xmlMapper = XmlMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();

        String xmlResponse = holidayWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getRestDeInfo")
                        .queryParam("solYear", year)
                        .queryParam("solMonth", String.format("%02d", month))
                        .queryParam("ServiceKey", externalApiProperties.holidayKey())
                        .build(false))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            HolidayXmlResponseDto response = xmlMapper.readValue(xmlResponse, HolidayXmlResponseDto.class);
            return response.holidayResponseBody().items().item();
        } catch (Exception e) {
            log.error("XML 파싱 실패: {}", e.getMessage());
            return List.of();
        }
    }
}
