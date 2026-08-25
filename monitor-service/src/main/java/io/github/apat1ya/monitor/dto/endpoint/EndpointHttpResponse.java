package io.github.apat1ya.monitor.dto.endpoint;

import java.time.Instant;

public record EndpointHttpResponse(
        Integer statusCode,
        String body,
        Long responseTime,
        Instant checkedAt
) {
}
