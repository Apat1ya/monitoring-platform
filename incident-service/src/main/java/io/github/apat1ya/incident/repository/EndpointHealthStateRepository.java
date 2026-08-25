package io.github.apat1ya.incident.repository;

import io.github.apat1ya.incident.entity.EndpointHealthState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EndpointHealthStateRepository extends JpaRepository<EndpointHealthState, Long> {
    boolean existsByEndpointId(Long endpointId);

    Optional<EndpointHealthState> findByEndpointId(Long endpointId);
}
