package io.github.apat1ya.auth.service;

import io.github.apat1ya.common.event.auth.EmailChangeRequestedEvent;
import io.github.apat1ya.common.event.auth.UserEmailChangedEvent;
import io.github.apat1ya.auth.dto.ChangeEmailRequest;
import io.github.apat1ya.auth.dto.EmailChangeData;
import io.github.apat1ya.auth.entity.UserEntity;
import io.github.apat1ya.auth.exception.EmailAlreadyInUseException;
import io.github.apat1ya.auth.exception.InvalidEmailChangeException;
import io.github.apat1ya.auth.exception.InvalidPasswordException;
import io.github.apat1ya.auth.exception.UserNotFoundException;
import io.github.apat1ya.auth.messaging.producer.ChangeEmailProducer;
import io.github.apat1ya.auth.messaging.producer.UserEmailChangedProducer;
import io.github.apat1ya.auth.repository.UserRepository;
import io.github.apat1ya.auth.service.support.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CurrentUserProvider currentUser;
    private final PasswordEncoder passwordEncoder;
    private final ChangeEmailProducer producer;
    private final UserEmailChangedProducer userEmailChangedProducer;
    private final RedisTemplate<String, EmailChangeData> redisTemplate;
    private static final Duration TOKEN_TTL = Duration.ofMinutes(10);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public Long findUserIdByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found by email"));
        return user.getId();
    }

    public void requestEmailChange(ChangeEmailRequest request) {
        UserEntity user = userRepository.findById(currentUser.getCurrentUserId())
                .orElseThrow(() -> new UserNotFoundException ("user not found"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyInUseException("Email already in use"); //409 conflict
        }

        String token = generateToken();

        EmailChangeData data = new EmailChangeData(
                user.getId(),
                request.email()
        );

        redisTemplate.opsForValue().set(
                "email-change:" + token,
                data,
                TOKEN_TTL
        );

        producer.send(new EmailChangeRequestedEvent(
                user.getId(),
                request.email(),
                token
        ));
    }

    public void confirmEmailChange(String token) {
        String key = "email-change:" + token;

        EmailChangeData data = redisTemplate.opsForValue().get(key);

        if (data == null) {
            throw new InvalidEmailChangeException("Token is invalid or expired");
        }

        UserEntity user = userRepository.findById(data.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found by id"));

        if (userRepository.existsByEmail(data.email())) {
            throw new EmailAlreadyInUseException("Email already in use"); //409 conflict
        }

        user.setEmail(data.email());
        userRepository.save(user);

        userEmailChangedProducer.send(new UserEmailChangedEvent(
                data.userId(),
                data.email()
        ) );
        redisTemplate.delete(key);
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
