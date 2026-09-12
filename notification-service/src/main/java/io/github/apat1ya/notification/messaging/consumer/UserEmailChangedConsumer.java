package io.github.apat1ya.notification.messaging.consumer;

import event.auth.UserEmailChangedEvent;
import io.github.apat1ya.notification.service.NotificationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEmailChangedConsumer {
    private final NotificationUserService notificationUserService;

    @KafkaListener(topics = "${app.kafka.topics.user-email-changed}")
    public void consume(UserEmailChangedEvent event) {
        notificationUserService.changeUserEmail(event.userId(), event.email());
    }

}
