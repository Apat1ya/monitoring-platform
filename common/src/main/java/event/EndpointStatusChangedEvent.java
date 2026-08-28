package event;

public record EndpointStatusChangedEvent(
        Long endpointId,
        String status
) {
}
