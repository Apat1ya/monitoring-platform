package io.github.apat1ya.incident.service;

import event.CheckResultEvent;
import io.github.apat1ya.incident.entity.EndpointHealthState;
import io.github.apat1ya.incident.entity.EndpointStatus;
import io.github.apat1ya.incident.repository.EndpointHealthStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckResultHandler {
    private final EndpointHealthStateRepository endpointHealthStateRepository;
    private final IncidentService incidentService;
    //todo отправлять ответ сервису монитора про изменение статуса endpoint

    private static final int FAILURE_THRESHOLD = 3;

    private void handle(CheckResultEvent checkResultEvent) {
        if (!endpointHealthStateRepository.existsByEndpointId(checkResultEvent.endpointId())) {
            EndpointHealthState endpointHealthState = new EndpointHealthState();
            endpointHealthState.setEndpointId(checkResultEvent.endpointId());
            endpointHealthState.setCounter(0);
            endpointHealthState.setStatus(EndpointStatus.valueOf(checkResultEvent.status()));
            endpointHealthStateRepository.save(endpointHealthState);
        }


        if(checkResultEvent.success()) {
            handleSuccess(checkResultEvent);
        } else {
            handleFail(checkResultEvent);
        }
    }

    private void handleFail(CheckResultEvent result) {
        EndpointHealthState endpointHealthState = endpointHealthStateRepository.findByEndpointId(result.endpointId())
                .orElseThrow(() -> new RuntimeException("EndpointHealthState not found by id"));

        if ("DOWN".equals(result.status())) {
            endpointHealthState.setCounter(FAILURE_THRESHOLD);
            endpointHealthStateRepository.save(endpointHealthState);
        }

        int counter = endpointHealthState.getCounter() + 1;
        if (counter >= FAILURE_THRESHOLD) {
            endpointHealthState.setStatus(EndpointStatus.DOWN);
            incidentService.openIncident(endpointHealthState);
        }

        endpointHealthState.setCounter(FAILURE_THRESHOLD);
        endpointHealthStateRepository.save(endpointHealthState);
    }

    private void handleSuccess(CheckResultEvent result) {
        EndpointHealthState endpointHealthState = endpointHealthStateRepository.findByEndpointId(result.endpointId())
                .orElseThrow(() -> new RuntimeException("EndpointHealthState not found by id"));

        if ("DOWN".equals(result.status())) {
            int counter = endpointHealthState.getCounter() - 1;

            if (counter <= 0) {
                counter = 0;
                endpointHealthState.setStatus(EndpointStatus.UP);
                incidentService.closeIncident(endpointHealthState);
            }
            endpointHealthState.setCounter(counter);
            endpointHealthStateRepository.save(endpointHealthState);
        }

        endpointHealthState.setCounter(0);
        endpointHealthStateRepository.save(endpointHealthState);
    }
}

