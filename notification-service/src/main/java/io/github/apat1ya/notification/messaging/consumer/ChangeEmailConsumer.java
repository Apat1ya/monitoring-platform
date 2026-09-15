package io.github.apat1ya.notification.messaging.consumer;

import io.github.apat1ya.common.event.auth.EmailChangeRequestedEvent;
import io.github.apat1ya.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeEmailConsumer {
    private final EmailService emailService;

    @KafkaListener(topics = "${app.kafka.topics.email-change-requested}")
    public void consume(EmailChangeRequestedEvent event) {
        emailService.sendEmailChangeConfirmation(
                event.email(),
                event.token()
        );
    }
}
