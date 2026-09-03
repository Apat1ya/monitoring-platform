package io.github.apat1ya.notification.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramNotificationException extends RuntimeException {
    public TelegramNotificationException(String failedToSendWelcomeMessage, TelegramApiException e) {
    }
}
