package com.example.springdemo.helper;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class Exceptions extends ResponseStatusException {
    public Exceptions(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public static class InvalidInputException extends ResponseStatusException {

        public InvalidInputException(HttpStatusCode status, String reason) {
            super(status, reason);
        }
    }
}
