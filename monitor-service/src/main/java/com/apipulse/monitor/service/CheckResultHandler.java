package com.apipulse.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.apipulse.monitor.entity.CheckResult;
import com.apipulse.monitor.entity.EndpointEntity;
import com.apipulse.monitor.entity.EndpointStatus;
import com.apipulse.monitor.repository.EndpointRepository;

@Component
@RequiredArgsConstructor
public class CheckResultHandler {
    private final IncidentService incidentService;
    private final EndpointRepository endpointRepository;
    private static final int FAILURE_THRESHOLD = 3;

    public void processCheckResult(EndpointEntity endpoint, CheckResult result) {
        if (result.isSuccess()) {
            handleSuccess(endpoint);
        } else {
            handleFail(endpoint);
        }
    }

    private void handleFail(EndpointEntity endpoint) {
        if (endpoint.getStatus()==EndpointStatus.DOWN) {
            endpoint.setFailureCounter(FAILURE_THRESHOLD);
            endpointRepository.save(endpoint);
            return;
        }

        int counter = endpoint.getFailureCounter() + 1;
        if (counter >= FAILURE_THRESHOLD) {
            counter = FAILURE_THRESHOLD;
            endpoint.setStatus(EndpointStatus.DOWN);
            incidentService.openIncident(endpoint);
        }
        endpoint.setFailureCounter(counter);
        endpointRepository.save(endpoint);
    }

    private void handleSuccess(EndpointEntity endpoint) {
        if (endpoint.getStatus()==EndpointStatus.DOWN) {
            int counter = endpoint.getFailureCounter() - 1;

            if (counter <= 0) {
                counter = 0;
                endpoint.setStatus(EndpointStatus.UP);
                incidentService.closeIncident(endpoint);
            }
            endpoint.setFailureCounter(counter);
            endpointRepository.save(endpoint);
            return;
        }
        endpoint.setFailureCounter(0);
        endpointRepository.save(endpoint);
    }
}

