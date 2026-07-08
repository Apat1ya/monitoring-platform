package com.apipulse.monitor.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.apipulse.monitor.entity.member.MonitorMember;
import com.apipulse.monitor.entity.member.Role;

public interface MemberMonitorRepository extends JpaRepository<MonitorMember, Long> {

    Optional<Role> findRoleByUserIdAndMonitorId(Long currentUserId, Long MonitorId);

    void deleteAllByMonitorId(Long monitorId);

    Optional<MonitorMember> findByUserIdAndMonitorId(Long addedUserId, Long monitorId);
}
