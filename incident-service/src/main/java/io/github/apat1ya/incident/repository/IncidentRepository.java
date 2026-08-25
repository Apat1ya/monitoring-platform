package io.github.apat1ya.incident.repository;

import io.github.apat1ya.incident.entity.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {
    boolean existsByEndpointId();

    Optional<IncidentEntity> findByEndpointId(Long endpointId);
}
