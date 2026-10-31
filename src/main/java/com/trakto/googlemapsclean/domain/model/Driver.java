package com.trakto.googlemapsclean.domain.model;

public record Driver(String id,
                     String name,
                     GeoPoint location) {

    public Driver {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        if (location == null) throw new IllegalArgumentException("location is required");
    }
}