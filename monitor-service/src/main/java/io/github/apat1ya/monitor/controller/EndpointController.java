package io.github.apat1ya.monitor.controller;

import io.github.apat1ya.monitor.dto.endpoint.EndpointActivityDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointCreateDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointEditDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointResponseDto;
import io.github.apat1ya.monitor.service.EndpointService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/{monitorId}/endpoint")
public class EndpointController {
    private final EndpointService endpointService;


    @GetMapping
    public Page<EndpointResponseDto> getAll(@PathVariable Long monitorId, Pageable pageable) {
        return endpointService.getAll(monitorId, pageable);
    }

    @PostMapping("/create")
    public EndpointResponseDto create(@PathVariable Long monitorId, @RequestBody @Valid EndpointCreateDto requestDto) {
        return endpointService.create(monitorId, requestDto);
    }

    @PatchMapping("/edit/{endpointId}")
    public EndpointResponseDto edit(@PathVariable Long monitorId,
                                    @PathVariable Long endpointId,
                                    @RequestBody @Valid EndpointEditDto requestDto) {
        return endpointService.edit(monitorId, endpointId, requestDto);
    }

    @PatchMapping("/activity/{endpointId}")
    public EndpointResponseDto isActive(@PathVariable Long monitorId,
                                        @PathVariable Long endpointId,
                                        @RequestBody EndpointActivityDto requestDto) {
        return endpointService.active(monitorId, endpointId, requestDto);
    }

    @DeleteMapping("/delete/{endpointId}")
    public void delete(@PathVariable Long monitorId, @PathVariable Long endpointId) {
        endpointService.delete(monitorId, endpointId);
    }
}
