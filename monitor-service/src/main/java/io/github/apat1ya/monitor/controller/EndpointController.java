package io.github.apat1ya.monitor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.github.apat1ya.monitor.dto.endpoint.EndpointActivityDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointCreateDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointEditDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointResponseDto;
import io.github.apat1ya.monitor.service.EndpointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{monitorId}/endpoint")
public class EndpointController {
    private final EndpointService endpointService;
    private final CheckResultService checkResultService;

    @GetMapping
    public Page<EndpointResponseDto> getAll(@PathVariable Long monitorId, Pageable pageable) {
        return endpointService.getAll(monitorId, pageable);
    }

//    @GetMapping("/{endpointId}/logs")
//    public Page<CheckResultResponse> getLogs(@PathVariable Long endpointId, Pageable pageable) {
//        return checkResultService.getAll(endpointId,pageable);
//    }

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

    @PatchMapping("/activity/{monitorId}")
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
