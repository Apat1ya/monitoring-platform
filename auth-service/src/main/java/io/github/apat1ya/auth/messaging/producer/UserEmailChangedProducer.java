package io.github.apat1ya.auth.messaging.producer;

import io.github.apat1ya.common.event.auth.UserEmailChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEmailChangedProducer {
    @Value("${app.kafka.topics.user-email-changed}")
    private String TOPIC;
    private final KafkaTemplate<String, UserEmailChangedEvent> kafkaTemplate;

    public void send(UserEmailChangedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.userId().toString(),
                event
        );
    }
}
