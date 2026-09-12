package io.github.apat1ya.auth.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String invalidPassword) {
        super(invalidPassword);
    }
}
