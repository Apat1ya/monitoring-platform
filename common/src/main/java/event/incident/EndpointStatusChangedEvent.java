package event.incident;

public record EndpointStatusChangedEvent(
        Long endpointId,
        String status
) {
}
