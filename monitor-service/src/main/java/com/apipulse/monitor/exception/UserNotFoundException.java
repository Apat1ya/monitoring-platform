package com.apipulse.monitor.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFoundByEmail) {
    }
}
