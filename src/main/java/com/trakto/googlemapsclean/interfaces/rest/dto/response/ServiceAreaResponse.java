package com.trakto.googlemapsclean.interfaces.rest.dto.response;

public record ServiceAreaResponse(
        boolean accepted,
        String status,
        String message
) {}