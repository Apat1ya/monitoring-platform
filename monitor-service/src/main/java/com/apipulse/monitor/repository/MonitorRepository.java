package com.apipulse.monitor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.apipulse.monitor.dto.monitor.MonitorResponseDto;
import com.apipulse.monitor.entity.MonitorEntity;

@Repository
public interface MonitorRepository extends JpaRepository<MonitorEntity, Long> {
    @Query("""
            select distinct m from monitor m
            join monitor_members mm on mm.monitor.id = m.monitorId
            where mm.userId = :userId
""")
    Page<MonitorEntity> findAllByUserId(Pageable pageable, Long userId);
}
