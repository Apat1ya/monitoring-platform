package com.apipulse.monitor.service.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.apipulse.monitor.entity.member.Role;
import com.apipulse.monitor.exception.NoAccessException;
import com.apipulse.monitor.repository.MemberMonitorRepository;

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
