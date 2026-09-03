package io.github.apat1ya.notification.messaging.consumer;

import event.monitor.member.MonitorMemberChangedEvent;
import io.github.apat1ya.notification.service.NotificationSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSubscriptionEventConsumer {
    private final NotificationSubscriptionService subscriptionService;

    @KafkaListener(topics = "${app.kafka.topics.notification-subscription}")
    public void consume(MonitorMemberChangedEvent event) {
        subscriptionService.handle(event);
    }
}
