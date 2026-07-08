package com.apipulse.monitor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apipulse.monitor.entity.CheckResult;

@Repository
public interface CheckResultRepository extends JpaRepository<CheckResult, Long> {
    Page<CheckResult> findAllByEndpointId(Pageable pageable, Long endpointId);
}
