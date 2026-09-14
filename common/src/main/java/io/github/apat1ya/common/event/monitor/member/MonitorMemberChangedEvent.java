package io.github.apat1ya.common.event.monitor.member;

public record MonitorMemberChangedEvent(
        Long userId,
        Long monitorId,
        MemberChangeType changeType
) {
}
