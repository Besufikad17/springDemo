package com.example.springdemo.helper;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class Exceptions extends Exception {
    public Exceptions(String reason) {
        super(reason);
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String reason) {
            super(reason);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String reason) {
            super(reason);
        }
    }

    public static class UserExistsException extends Exception {
        public UserExistsException(String reason) {
            super(reason);
        }
    }

    public static class UnAuthorizedException extends Exception {
        public UnAuthorizedException(String message) {
            super(message);
        }
    }
}
