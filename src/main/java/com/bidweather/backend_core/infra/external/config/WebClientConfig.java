package com.bidweather.backend_core.infra.external.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    private HttpClient defaultHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(10));
    }

    @Bean
    public WebClient kmaWebClient() {
        HttpClient httpClient = defaultHttpClient()
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10)));

        return WebClient.builder()
                .baseUrl("https://apihub.kma.go.kr")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient holidayWebClient() {
        HttpClient httpClient = defaultHttpClient();

        return WebClient.builder()
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
