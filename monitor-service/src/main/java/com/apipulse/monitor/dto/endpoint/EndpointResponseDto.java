package com.apipulse.monitor.dto.endpoint;

import org.springframework.http.HttpMethod;
import com.apipulse.monitor.entity.EndpointStatus;

public record EndpointResponseDto(
        HttpMethod httpMethod,
        String path,
        String body,
        boolean active,
        Integer checkIntervalSeconds,
        EndpointStatus status
) {
}
