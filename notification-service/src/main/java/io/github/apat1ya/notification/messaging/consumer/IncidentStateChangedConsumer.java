package io.github.apat1ya.notification.messaging.consumer;

import event.incident.StateChangedEvent;
import io.github.apat1ya.notification.repository.NotificationSubscriptionRepository;
import io.github.apat1ya.notification.telegram.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IncidentStateChangedConsumer {
    private final TelegramSender telegramSender;
    private final NotificationSubscriptionRepository subscriptionRepository;

    @KafkaListener(topics = "${app.kafka.topics.state-changed}")
    public void consume(StateChangedEvent event) {
        List<Long> chatIds = subscriptionRepository.findTelegramChatIdsByMonitorId(event.monitorId());
        chatIds.forEach(chatId -> telegramSender.sendIncidentStateChanged(chatId, event));
    }
}
