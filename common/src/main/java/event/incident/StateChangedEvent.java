package event.incident;

import java.time.Instant;

public record StateChangedEvent(
        Long monitorId,
        Long endpointId,
        String checkedUrl,
        String status,
        Integer statusCode,
        Instant startedAt,
        Instant resolvedAt,
        String errorMessage
) {
}
