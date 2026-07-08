package com.apipulse.monitor.dto.endpoint;

public record EndpointHttpResponse(
        Integer statusCode,
        String body,
        Long responseTime,
        Long checkedAt
) {
}
