package event.auth;

import java.time.Instant;

public record EmailChangeRequestedEvent(
        Long userId,
        String newEmail,
        String tokenHash,
        Instant expiresAt
) {
}
