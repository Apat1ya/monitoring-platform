package io.github.apat1ya.common.event.auth;

public record EmailChangeRequestedEvent(
        Long userId,
        String email,
        String token) {
}
