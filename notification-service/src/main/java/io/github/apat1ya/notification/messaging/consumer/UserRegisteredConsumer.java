package io.github.apat1ya.notification.messaging.consumer;

import event.auth.UserRegisteredEvent;
import io.github.apat1ya.notification.service.NotificationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredConsumer {
    private final NotificationUserService notificationUserService;

    @KafkaListener(topics = "${app.kafka.topics.user-registered}")
    public void consumeNewUser(UserRegisteredEvent userRegisteredEvent) {
        notificationUserService.addNewUser(userRegisteredEvent);
    }
}
