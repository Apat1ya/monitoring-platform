package io.github.apat1ya.monitor.dto.endpoint;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;

public record EndpointCreateDto(
        @NotNull
        HttpMethod httpMethod,
        @NotBlank
        String path,
        String body,
        @Min(value = 60, message = "The value cannot be less than 60")
        Integer checkIntervalSeconds,
        @NotNull
        int expectedStatusCode
) {
}
