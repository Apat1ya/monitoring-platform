package event.monitor.member;

public record MemberRemovedEvent(
        Long userId,
        Long monitorId
) {
}
