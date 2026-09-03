package io.github.apat1ya.incident.messaging.producer;

import event.incident.StateChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncidentStateChangeProducer {
    @Value("${app.kafka.topics.state-changed}")
    private String TOPIC;
    private final KafkaTemplate<String, StateChangedEvent> kafkaTemplate;

    public void send(StateChangedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.monitorId().toString(),
                event
        );
    }
}
