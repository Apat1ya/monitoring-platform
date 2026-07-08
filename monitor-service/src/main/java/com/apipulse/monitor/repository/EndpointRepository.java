package com.apipulse.monitor.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apipulse.monitor.entity.EndpointEntity;

@Repository
public interface EndpointRepository extends JpaRepository<EndpointEntity, Long> {
    Page<EndpointEntity> findAllByMonitorId(Pageable pageable, Long monitorId);

    Optional<EndpointEntity> findByIdAndMonitorId(Long endpointId, Long monitorId);

    List<EndpointEntity> findAllByActiveTrue();
}
