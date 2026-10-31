package com.trakto.googlemapsclean.interfaces.rest.dto.response;

public record NearbyDriverResponse(
        String id,
        String name,
        double latitude,
        double longitude,
        double distanceKm
) {}