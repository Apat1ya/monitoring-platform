package io.github.apat1ya.notification.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String userNotFount) {
        super(userNotFount);
    }
}
