package event.auth;

public record EmailChangeRequestedEvent(
        Long userId,
        String newEmail,
        String token) {
}
