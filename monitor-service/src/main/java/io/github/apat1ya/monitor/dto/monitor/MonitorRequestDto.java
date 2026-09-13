package io.github.apat1ya.monitor.dto.monitor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MonitorRequestDto(
        @NotBlank
        String target,
        @Size(max = 600)
        String description,
        boolean active
) {
}
