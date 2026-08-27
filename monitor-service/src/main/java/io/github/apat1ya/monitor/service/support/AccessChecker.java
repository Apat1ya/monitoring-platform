package io.github.apat1ya.monitor.service.support;

import io.github.apat1ya.monitor.entity.member.Role;
import io.github.apat1ya.monitor.exception.NoAccessException;
import io.github.apat1ya.monitor.repository.MemberMonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessChecker {
    private final MemberMonitorRepository memberMonitorRepository;

    public void checkAccessEdit(Long currentUserId, Long monitorId) {
        Role role = memberMonitorRepository
                .findRoleByUserIdAndMonitorId(currentUserId, monitorId)
                .orElseThrow(() -> new NoAccessException("No access to this monitor"));
        if (role != Role.OWNER && role != Role.EDIT) {
            throw new NoAccessException("Not enough permissions");
        }
    }

    public void checkAccessOwner(Long currentUserId, Long monitorId) {
        Role role = memberMonitorRepository
                .findRoleByUserIdAndMonitorId(currentUserId, monitorId)
                .orElseThrow(() -> new NoAccessException("No access to this monitor"));
        if (role != Role.OWNER) {
            throw new NoAccessException("Not enough permissions");
        }
    }
}
