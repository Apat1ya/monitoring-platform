package io.github.apat1ya.monitor.messaging.producer;

import event.CheckResultEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckResultProducer {
    @Value("${app.kafka.topics.check-results}")
    private String TOPIC;
    private final KafkaTemplate<String, CheckResultEvent> kafkaTemplate;

    public void send(CheckResultEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.endpointId().toString(),
                event
        );
    }
}
