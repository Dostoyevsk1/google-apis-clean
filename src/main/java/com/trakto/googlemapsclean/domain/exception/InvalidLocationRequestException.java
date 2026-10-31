package com.trakto.googlemapsclean.domain.exception;

public class InvalidLocationRequestException extends RuntimeException {

    public InvalidLocationRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}