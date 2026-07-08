package com.apipulse.monitor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apipulse.monitor.dto.monitor.MonitorRequestDto;
import com.apipulse.monitor.dto.monitor.MonitorResponseDto;
import com.apipulse.monitor.service.MonitorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitors")
public class MonitorController {
    private final MonitorService monitorService;

    @PostMapping("/create")
    public MonitorResponseDto create(@RequestBody @Valid MonitorRequestDto requestDto) {
        return monitorService.create(requestDto);
    }

    @PatchMapping("/edit/{monitorId}")
    public MonitorResponseDto update(@PathVariable Long monitorId
            ,@RequestBody MonitorRequestDto requestDto) {
        return monitorService.update(monitorId, requestDto);
    }

    @GetMapping
    public Page<MonitorResponseDto> getAllMonitors(Pageable pageable) {
        return monitorService.getAllMonitors(pageable);
    }

    @DeleteMapping("/{monitorId}")
    public void delete(@PathVariable Long monitorId) {
        monitorService.delete(monitorId);
    }
}
