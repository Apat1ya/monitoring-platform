package io.github.apat1ya.incident.service;

import event.CheckResultEvent;
import event.EndpointStatusChangedEvent;
import io.github.apat1ya.incident.entity.EndpointHealthState;
import io.github.apat1ya.incident.entity.EndpointStatus;
import io.github.apat1ya.incident.messaging.producer.EndpointChangedStatusProducer;
import io.github.apat1ya.incident.repository.EndpointHealthStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckResultHandler {
    private final EndpointHealthStateRepository endpointHealthStateRepository;
    private final IncidentService incidentService;
    private final EndpointChangedStatusProducer producer;

    private static final int FAILURE_THRESHOLD = 3;

    public void handle(CheckResultEvent checkResultEvent) {
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

        int counter = Math.min(endpointHealthState.getCounter() + 1,
                                FAILURE_THRESHOLD);
        if (counter == FAILURE_THRESHOLD
                && endpointHealthState.getStatus() != EndpointStatus.DOWN) {
            endpointHealthState.setStatus(EndpointStatus.DOWN);
            producer.send(new EndpointStatusChangedEvent(
                    result.endpointId(),
                    EndpointStatus.DOWN.toString()
            ));
            incidentService.openIncident(endpointHealthState, result);
        }

        endpointHealthState.setCounter(counter);
        endpointHealthStateRepository.save(endpointHealthState);
    }

    private void handleSuccess(CheckResultEvent result) {
        EndpointHealthState endpointHealthState = endpointHealthStateRepository.findByEndpointId(result.endpointId())
                .orElseThrow(() -> new RuntimeException("EndpointHealthState not found by id"));

        int counter = Math.max(endpointHealthState.getCounter() - 1, 0);

        if (endpointHealthState.getStatus() == EndpointStatus.DOWN && counter == 0) {
            endpointHealthState.setStatus(EndpointStatus.UP);
            producer.send(new EndpointStatusChangedEvent(
                    result.endpointId(),
                    EndpointStatus.UP.toString()
            ));
            incidentService.closeIncident(endpointHealthState);
        }

        endpointHealthState.setCounter(counter);
        endpointHealthStateRepository.save(endpointHealthState);
    }
}

