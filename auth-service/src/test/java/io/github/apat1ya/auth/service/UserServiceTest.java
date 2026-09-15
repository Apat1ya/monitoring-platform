package io.github.apat1ya.auth.service;

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
import io.github.apat1ya.common.event.auth.EmailChangeRequestedEvent;
import io.github.apat1ya.common.event.auth.UserEmailChangedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CurrentUserProvider currentUser;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ChangeEmailProducer producer;
    @Mock
    private RedisTemplate<String, EmailChangeData> redisTemplate;
    @Mock
    private ValueOperations<String, EmailChangeData> valueOperations;
    @Mock
    private UserEmailChangedProducer userEmailChangedProducer;

    private Long userId;
    private ChangeEmailRequest request;
    private UserEntity user;
    private String token;
    private String key;
    private String newEmail;
    private EmailChangeData data;

    @BeforeEach
    void setup() {
        userId = 1L;

        request = new ChangeEmailRequest(
                "test@test.com",
                "Password123!"
        );

        user = new UserEntity();
        user.setId(userId);
        user.setPassword("encodedPassword");

        token = "token";
        key = "email-change:" + token;
        newEmail = "new@test.com";
        data = new EmailChangeData(
                userId,
                newEmail
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(currentUser.getCurrentUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(true);
        when(userRepository.existsByEmail(request.email()))
                .thenReturn(true);


        assertThrows(EmailAlreadyInUseException.class,
                () -> userService.requestEmailChange(request));

        verify(userRepository, never()).save(any());
        verify(producer, never()).send(any());
    }

    @Test
    void shouldReturnUserIdWhenUserExists() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        Long result = userService.findUserIdByEmail(user.getEmail());

        assertEquals(userId, result);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenEmailDoesNotExist() {
        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findUserIdByEmail(request.email()));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCurrentUserDoesNotExist() {
        when(currentUser.getCurrentUserId()).thenReturn(userId);
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.requestEmailChange(request));

        verify(producer, never()).send(any());
    }

    @Test
    void shouldThrowInvalidPasswordExceptionWhenPasswordIsIncorrect() {
        when(currentUser.getCurrentUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(false);

        assertThrows(InvalidPasswordException.class,
                () -> userService.requestEmailChange(request));

        verify(userRepository, never()).save(any());
        verify(producer, never()).send(any());
    }

    @Test
    void shouldRequestEmailChangeWhenRequestIsValid() {
        ArgumentCaptor<EmailChangeRequestedEvent> eventCaptor =
                ArgumentCaptor.forClass(EmailChangeRequestedEvent.class);

        when(currentUser.getCurrentUserId()).thenReturn(userId);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(true);
        when(userRepository.existsByEmail(request.email()))
                .thenReturn(false);
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        userService.requestEmailChange(request);

        verify(producer).send(eventCaptor.capture());

        EmailChangeRequestedEvent event = eventCaptor.getValue();

        assertEquals(user.getId(), event.userId());
        assertEquals(request.email(), event.email());
        assertNotNull(event.token());
        assertFalse(event.token().isBlank());
    }

    @Test
    void shouldThrowInvalidEmailChangeExceptionWhenTokenIsInvalidOrExpired() {
        String token = "token";
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
        when(valueOperations.get("email-change:" + token))
                .thenReturn(null);
        assertThrows(InvalidEmailChangeException.class,
                () -> userService.confirmEmailChange(token));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserFromTokenDoesNotExist() {
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
        when(valueOperations.get(key))
                .thenReturn(data);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.confirmEmailChange(token));

        verify(userRepository, never()).save(any());
        verify(producer, never()).send(any());
    }

    @Test
    void shouldConfirmEmailChangeWhenTokenIsValid() {
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
        when(valueOperations.get(key))
                .thenReturn(data);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(data.email()))
                .thenReturn(false);

        userService.confirmEmailChange(token);

        assertEquals(newEmail, user.getEmail());

        verify(userRepository).save(user);
        verify(userEmailChangedProducer).send(
                new UserEmailChangedEvent(
                        userId,
                        newEmail
                )
        );
        verify(redisTemplate).delete(key);
    }
}