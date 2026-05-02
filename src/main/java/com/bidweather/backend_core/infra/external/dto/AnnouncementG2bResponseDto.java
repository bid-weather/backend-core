package com.bidweather.backend_core.infra.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.catalina.connector.Response;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AnnouncementG2bResponseDto(Response response) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(Body body) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Body(List<Item> items, int totalCount) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
            String bidNtceNo,
            String bidNtceOrd,
            String bidNtceNm,
            String bidNtceDt,
            String ntceInsttNm,
            String dminsttNm
    ) {
        public String getAnnouncementNo() {
            return bidNtceOrd != null && !bidNtceOrd.isEmpty()
                    ? bidNtceNo + "-" + bidNtceOrd
                    : bidNtceNo;
        }

        public String getAnnouncementDate() {
            return bidNtceDt != null && bidNtceDt.length() >= 10
                    ? bidNtceDt.substring(0, 10)
                    : bidNtceDt;
        }
    }
}