package com.qwerty.microservices.userservicefront.security.resource;

class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
