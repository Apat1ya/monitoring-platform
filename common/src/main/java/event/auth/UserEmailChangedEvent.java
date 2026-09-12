package event.auth;

public record UserEmailChangedEvent(
        Long userId,
        String email
) {
}
