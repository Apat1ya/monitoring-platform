package event.monitor.member;

public record MemberAddedEvent(
        Long userId,
        Long monitorId
) {
}
