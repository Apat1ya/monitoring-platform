package event.monitor.member;

public record MonitorMemberChangedEvent(
        Long userId,
        Long monitorId,
        MemberChangeType changeType
) {
}
