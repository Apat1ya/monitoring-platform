package io.github.apat1ya.monitor.dto.monitor;

import jakarta.validation.constraints.NotBlank;

public record MonitorRequestDto(
        @NotBlank
        String target,
        String description,
        boolean active
) {
}
