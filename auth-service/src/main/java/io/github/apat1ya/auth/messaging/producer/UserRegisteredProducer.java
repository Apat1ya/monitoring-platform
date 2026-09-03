package io.github.apat1ya.auth.messaging.producer;

import event.auth.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisteredProducer {
    @Value("${app.kafka.topics.user-registered}")
    private String TOPIC;
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void send(UserRegisteredEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.userId().toString(),
                event
        );
    }
}
