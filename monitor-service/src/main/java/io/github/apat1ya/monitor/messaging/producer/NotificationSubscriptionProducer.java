package io.github.apat1ya.monitor.messaging.producer;

import event.monitor.member.MonitorMemberChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSubscriptionProducer {
    @Value("${monitor.notification-subscription}")
    private String TOPIC;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMemberChangedEvent(MonitorMemberChangedEvent monitorMemberChangedEvent) {
        kafkaTemplate.send(
                TOPIC,
                monitorMemberChangedEvent.userId().toString(),
                monitorMemberChangedEvent
        );
    }

}
