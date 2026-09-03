package io.github.apat1ya.incident.service;

import event.monitor.check.CheckResultEvent;
import io.github.apat1ya.incident.entity.EndpointHealthState;
import io.github.apat1ya.incident.entity.IncidentEntity;
import io.github.apat1ya.incident.entity.Status;
import io.github.apat1ya.incident.mapper.IncidentEventMapper;
import io.github.apat1ya.incident.messaging.producer.IncidentStateChangeProducer;
import io.github.apat1ya.incident.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final IncidentStateChangeProducer stateChangeProducer;
    private final IncidentEventMapper incidentEventMapper;

    public void openIncident(EndpointHealthState endpointHealthState, CheckResultEvent result) {
        if (incidentRepository.existsByEndpointId(result.endpointId())){
            throw new RuntimeException("Incident already open");
        }

        IncidentEntity incident = new IncidentEntity();
        incident.setMonitorId(result.monitorId());
        incident.setEndpointId(result.endpointId());
        incident.setCheckedUrl(result.checkedUrl());
        incident.setStatus(Status.OPEN);
        incident.setStatusCode(result.statusCode());
        incident.setStartedAt(result.startedAt());
        incident.setErrorMessage(result.errorMessage());

        incidentRepository.save(incident);
        stateChangeProducer.send(incidentEventMapper.toEventFromEntity(incident));
    }

    public void closeIncident(EndpointHealthState endpointHealthState) {
        IncidentEntity incident = incidentRepository.findByEndpointId(endpointHealthState.getEndpointId())
                .orElseThrow(() -> new RuntimeException("Incident not found by id"));
        incident.setResolvedAt(Instant.now());
        incidentRepository.save(incident);
        stateChangeProducer.send(incidentEventMapper.toEventFromEntity(incident));
    }
}
