package io.github.apat1ya.monitor.dto.monitor;

public record MonitorResponseDto(
        Long id,
        String target,
        String description,
        boolean active
) {
}
