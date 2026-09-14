package io.github.apat1ya.common.event.auth;

public record UserEmailChangedEvent(
        Long userId,
        String email
) {
}
