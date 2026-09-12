package io.github.apat1ya.auth.messaging.producer;

import event.auth.EmailChangeRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeEmailProducer {
    @Value("${app.kafka.topics.newEmail-change}")
    private String TOPIC;
    private final KafkaTemplate<String, EmailChangeRequestedEvent> kafkaTemplate;

    public void send(EmailChangeRequestedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.userId().toString(),
                event
        );
    }
}
