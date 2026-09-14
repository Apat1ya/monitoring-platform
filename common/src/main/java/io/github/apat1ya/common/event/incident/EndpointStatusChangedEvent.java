package io.github.apat1ya.common.event.incident;

public record EndpointStatusChangedEvent(
        Long endpointId,
        String status
) {
}
