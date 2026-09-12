package io.github.apat1ya.auth.exception;

public class InvalidEmailChangeException extends RuntimeException {
    public InvalidEmailChangeException(String tokenIsInvalidOrExpired) {
        super(tokenIsInvalidOrExpired);
    }
}
