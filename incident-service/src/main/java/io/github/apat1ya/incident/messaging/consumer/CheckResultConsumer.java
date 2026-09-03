package io.github.apat1ya.incident.messaging.consumer;

import event.monitor.check.CheckResultEvent;
import io.github.apat1ya.incident.service.CheckResultHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckResultConsumer {
    private final CheckResultHandler checkResultHandler;

    @KafkaListener(topics = "${app.kafka.topics.check-results}")
    public void consume(CheckResultEvent event) {
        checkResultHandler.handle(event);
    }
}
