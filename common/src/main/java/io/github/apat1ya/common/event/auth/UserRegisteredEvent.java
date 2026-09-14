package io.github.apat1ya.common.event.auth;

public record UserRegisteredEvent(
        Long userId,
        String email
) {
}
