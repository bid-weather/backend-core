package com.bidweather.backend_core.infra.external.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    private HttpClient createHttpClient(int timeoutSeconds) {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutSeconds * 1000)
                .responseTimeout(Duration.ofSeconds(timeoutSeconds))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(timeoutSeconds))
                                .addHandlerLast(new WriteTimeoutHandler(timeoutSeconds)));
    }

    @Bean
    public WebClient kmaWebClient() {
        HttpClient httpClient = createHttpClient(10);

        return WebClient.builder()
                .baseUrl("https://apihub.kma.go.kr")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient holidayWebClient() {
        HttpClient httpClient = createHttpClient(10);

        return WebClient.builder()
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient g2bWebClient() {
        HttpClient httpClient = createHttpClient(30);

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .baseUrl("https://apis.data.go.kr/1230000/ad/BidPublicInfoService")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(strategies)
                .build();
    }
}
