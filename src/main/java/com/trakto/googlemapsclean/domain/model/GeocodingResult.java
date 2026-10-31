package com.trakto.googlemapsclean.domain.model;

import java.util.List;
import java.util.Optional;

    public record GeocodingResult(List<GeocodedLocation> candidates) {

    public GeocodingResult {
        candidates = candidates == null ? List.of() : List.copyOf(candidates);
    }

    public boolean isEmpty() {
        return candidates.isEmpty();
    }

    public boolean isAmbiguous() {
        return candidates.size() > 1;
    }

    public int count() {
        return candidates.size();
    }

    public Optional<GeocodedLocation> best() {
        return candidates.stream().findFirst();
    }
}