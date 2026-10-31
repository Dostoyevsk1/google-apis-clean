package com.trakto.googlemapsclean.application.exception;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(String message) {
        super(message);
    }
}