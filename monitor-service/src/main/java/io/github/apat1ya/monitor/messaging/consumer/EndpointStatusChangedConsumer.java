package io.github.apat1ya.monitor.messaging.consumer;

import event.EndpointStatusChangedEvent;
import io.github.apat1ya.monitor.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndpointStatusChangedConsumer {
    private final EndpointService endpointService;

    @KafkaListener(topics = "${app.kafka.topics.endpoint-status-changed}")
    public void consume(EndpointStatusChangedEvent event) {
        endpointService.handle(event);
    }
}
