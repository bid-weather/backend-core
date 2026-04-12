package com.bidweather.backend_core.controller;

import com.bidweather.backend_core.dto.response.TimeResponseDto;
import com.bidweather.backend_core.service.query.SystemQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {
    private final SystemQueryService systemQueryService;

    @GetMapping("/time")
    public ResponseEntity<TimeResponseDto> getTime() {
        return ResponseEntity.ok(systemQueryService.getTime());
    }
}
