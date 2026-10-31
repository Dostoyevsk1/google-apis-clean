package com.trakto.googlemapsclean.domain.model;

import java.util.List;

public record GeocodedLocation(
        String formattedAddress,
        GeoPoint point,
        String placeId,
        Granularity granularity,
        List<String> types
) {

    public GeocodedLocation {
        if (formattedAddress == null || formattedAddress.isBlank()) {
            throw new IllegalArgumentException("formattedAddress is required");
        }
        if (point == null) {
            throw new IllegalArgumentException("point is required");
        }
        granularity = granularity == null ? Granularity.UNKNOWN : granularity;
        types = types == null ? List.of() : List.copyOf(types); // copia inmutable
    }

    public boolean isPrecise() {
        return granularity == Granularity.ROOFTOP;
    }
}