package io.github.apat1ya.notification.telegram;

import event.incident.StateChangedEvent;
import io.github.apat1ya.notification.exception.TelegramNotificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@RequiredArgsConstructor
public class TelegramSender {
    private final TelegramClient telegramClient;

    public void sendWelcome(Long chatId) {
        SendMessage message = new SendMessage(
                chatId.toString(),
                "Telegram successfully connected"
        );

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new TelegramNotificationException("Failed to send welcome message",e);
        }
    }

    public void sendIncidentStateChanged(Long chatId, StateChangedEvent event) {
        String text = switch (event.status()) {
            case "OPEN" -> """
                    Incident opened!
                    Monitor: %s
                    URL: %s
                    Status code: %s
                    Error: %s
                    Started at: %s
                    """.formatted(
                            event.monitorId(),
                            event.checkedUrl(),
                            event.statusCode(),
                            event.errorMessage(),
                            event.startedAt()
            );
            case "RESOLVED" -> """
                    Incident resolved!
                    Monitor: %s
                    URL: %s
                    Started at: %s
                    Resolved at: %s
                    """.formatted(
                            event.monitorId(),
                            event.checkedUrl(),
                            event.startedAt(),
                            event.resolvedAt()
            );
            default -> throw new IllegalArgumentException(
                    "Unknown incident status: " + event.status()
            );
        };

        SendMessage message = new SendMessage(chatId.toString(),text);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new TelegramNotificationException("Failed to send incident state changed message",e);
        }
    }
}
