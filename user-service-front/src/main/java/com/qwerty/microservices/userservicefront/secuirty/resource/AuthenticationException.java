package com.qwerty.microservices.userservicefront.secuirty.resource;

class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
