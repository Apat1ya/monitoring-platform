package io.github.apat1ya.monitor.dto.endpoint;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpMethod;

public record EndpointCreateDto(
        @NotBlank
        HttpMethod httpMethod,
        @NotBlank
        String path,
        String body,
        @Min(value = 60, message = "The value cannot be less than 60")
        Integer checkIntervalSeconds,
        @NotBlank
        int expectedStatusCode
) {
}
