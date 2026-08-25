package io.github.apat1ya.monitor.repository;

import io.github.apat1ya.monitor.entity.EndpointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndpointRepository extends JpaRepository<EndpointEntity, Long> {
    Page<EndpointEntity> findAllByMonitorId(Pageable pageable, Long monitorId);

    Optional<EndpointEntity> findByIdAndMonitorId(Long endpointId, Long monitorId);

    List<EndpointEntity> findAllByActiveTrue();
}
