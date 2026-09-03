package event.auth;

public record UserRegisteredEvent(
        Long userId,
        String email
) {
}
