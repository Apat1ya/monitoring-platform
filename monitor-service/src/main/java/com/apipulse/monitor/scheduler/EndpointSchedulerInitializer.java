package com.apipulse.monitor.scheduler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.apipulse.monitor.entity.EndpointEntity;
import com.apipulse.monitor.repository.EndpointRepository;

@Component
@RequiredArgsConstructor
public class EndpointSchedulerInitializer {
    private final EndpointSchedulerService endpointSchedulerService;
    private final EndpointRepository endpointRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        List<EndpointEntity> activeEndpoints = endpointRepository.findAllByActiveTrue();

        for (EndpointEntity endpoint : activeEndpoints) {
            endpointSchedulerService.scheduleEndpoint(endpoint.getId(), endpoint.getCheckIntervalSeconds());
        }
    }
}
