package com.apipulse.monitor.dto.checkresult;

import org.springframework.http.HttpMethod;

public record CheckResultResponse(
        String checkedUrl,
        boolean success,
        HttpMethod httpMethod,
        Integer statusCode,
        Long responseTime,
        String errorMessage,
        Long checkedAt
) {
}
