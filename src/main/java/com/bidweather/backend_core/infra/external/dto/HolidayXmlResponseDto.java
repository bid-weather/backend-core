package com.bidweather.backend_core.infra.external.dto;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public record HolidayXmlResponseDto(
        @JacksonXmlProperty(localName = "body")
        HolidayResponseBody holidayResponseBody
        ) {
    public record HolidayResponseBody(
            @JacksonXmlProperty(localName = "items")
            Items items
    ) {}

    public record Items(
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "item") List<HolidayItem> item
    ) {}

    public record HolidayItem(
            @JacksonXmlProperty(localName = "dateName")
            String dateName,

            @JacksonXmlProperty(localName = "locdate")
            String locdate
    ) {}
}
