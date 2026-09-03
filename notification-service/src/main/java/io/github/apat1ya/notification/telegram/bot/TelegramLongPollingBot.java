package io.github.apat1ya.notification.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
@RequiredArgsConstructor
public class TelegramLongPollingBot implements SpringLongPollingBot {
    private final TelegramUpdateConsumer telegramUpdateConsumer;
    @Value("${TELEGRAM_BOT_TOKEN}")
    private String token;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return telegramUpdateConsumer;
    }
}
