package com.bidweather.backend_core.infra.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.api")
public record ExternalApiProperties(
        String holidayKey,
        String kmaKey,
        String g2bKey
) {
}
