package io.github.apat1ya.incident.messaging.producer;

import event.EndpointStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointChangedStatusProducer {
    @Value("${app.kafka.topics.endpoint-status-changed}")
    private final String TOPIC;
    private final KafkaTemplate<String, EndpointStatusChangedEvent> kafkaTemplate;

    public void send(EndpointStatusChangedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.endpointId().toString(),
                event
        );
    }
}
