package event.monitor.check;

import java.time.Instant;

public record CheckResultEvent(
    Long monitorId,
    Long endpointId,
    String status,
    String checkedUrl,
    boolean success,
    String httpMethod,
    Integer statusCode,
    Long responseTime,
    String errorMessage,
    Instant startedAt
) {
}
