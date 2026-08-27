package io.github.apat1ya.monitor.repository;

import io.github.apat1ya.monitor.entity.member.MonitorMember;
import io.github.apat1ya.monitor.entity.member.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberMonitorRepository extends JpaRepository<MonitorMember, Long> {

    Optional<Role> findRoleByUserIdAndMonitorId(Long currentUserId, Long MonitorId);

    Optional<MonitorMember> findByUserIdAndMonitorId(Long addedUserId, Long monitorId);
}
