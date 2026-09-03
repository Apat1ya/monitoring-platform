package io.github.apat1ya.notification.controller;

import io.github.apat1ya.notification.dto.TelegramLinkResponse;
import io.github.apat1ya.notification.telegram.service.TelegramLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final TelegramLinkService telegramLinkService;

    @PostMapping("/telegram/link")
    public TelegramLinkResponse createLink() {
        return telegramLinkService.createLink();
    }
}
