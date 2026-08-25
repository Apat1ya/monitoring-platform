package io.github.apat1ya.monitor.scheduler;

import lombok.RequiredArgsConstructor;
import io.github.apat1ya.monitor.entity.EndpointEntity;
import io.github.apat1ya.monitor.repository.EndpointRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

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
