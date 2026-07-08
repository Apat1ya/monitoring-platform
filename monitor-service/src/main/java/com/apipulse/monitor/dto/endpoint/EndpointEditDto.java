package com.apipulse.monitor.dto.endpoint;

import jakarta.validation.constraints.Min;
import org.springframework.http.HttpMethod;

public record EndpointEditDto(
        HttpMethod httpMethod,
        String path,
        String body,
        boolean active,
        @Min(value = 60, message = "The value cannot be less than 60")
        Integer checkIntervalSeconds,
        int expectedStatusCode
) {
}
