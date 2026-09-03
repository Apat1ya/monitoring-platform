package io.github.apat1ya.notification.telegram.service;

import io.github.apat1ya.notification.dto.TelegramLinkResponse;
import io.github.apat1ya.notification.entity.NotificationUser;
import io.github.apat1ya.notification.exception.TelegramLinkException;
import io.github.apat1ya.notification.repository.NotificationUserRepository;
import io.github.apat1ya.notification.service.CurrentUserProvider;
import io.github.apat1ya.notification.telegram.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TelegramLinkService {
    private final CurrentUserProvider userProvider;
    private final StringRedisTemplate redisTemplate;
    private final NotificationUserRepository userRepository;
    private final TelegramSender telegramSender;
    private final Duration TOKEN_TLL = Duration.ofMinutes(10);
    private final SecureRandom SECURE_RANDOM = new SecureRandom();


    @Value("${TELEGRAM_BOT_USERNAME}")
    private String botUrl;

    public TelegramLinkResponse createLink() {
        Long userId = userProvider.getCurrentUserId();
        String token = generateToken();
        String key = "telegram:link:" + token;
        redisTemplate.opsForValue().set(
                key,
                userId.toString(),
                TOKEN_TLL
        );

        String url = botUrl + "?start=" + token;
        return new TelegramLinkResponse(url);
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public void confirm(String token, Long chatId) {
        String key = "telegram:link:" + token;
        String userIdValue = redisTemplate.opsForValue().getAndDelete(key);

        if (userIdValue == null) {
            throw new TelegramLinkException("link expired or invalid");
        }

        Long userId = Long.valueOf(userIdValue);
        NotificationUser user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found by id"));
        user.setTelegramChatId(chatId);
        userRepository.save(user);
        telegramSender.sendWelcome(chatId);
    }
}
