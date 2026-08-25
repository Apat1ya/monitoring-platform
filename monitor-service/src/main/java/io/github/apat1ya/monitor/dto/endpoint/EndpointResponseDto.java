package io.github.apat1ya.monitor.dto.endpoint;

import io.github.apat1ya.monitor.entity.EndpointStatus;
import org.springframework.http.HttpMethod;

public record EndpointResponseDto(
        HttpMethod httpMethod,
        String path,
        String body,
        boolean active,
        Integer checkIntervalSeconds,
        EndpointStatus status
) {
}
