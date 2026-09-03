package io.github.apat1ya.notification.telegram.bot;

import io.github.apat1ya.notification.telegram.service.TelegramLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramUpdateConsumer implements LongPollingUpdateConsumer {
    private final TelegramLinkService telegramLinkService;

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(this::handleUpdates);
    }

    private void handleUpdates(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText().trim();

        if (!text.startsWith("/start")) {
            return;
        }

        String token = text.substring("/start".length()).trim();

        if (token.isBlank()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();

        telegramLinkService.confirm(token, chatId);
    }
}
