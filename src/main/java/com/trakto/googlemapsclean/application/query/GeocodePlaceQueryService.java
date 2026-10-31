package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.application.exception.LocationNotFoundException;
import com.trakto.googlemapsclean.application.port.LocationCache;
import com.trakto.googlemapsclean.domain.model.GeocodedLocation;
import com.trakto.googlemapsclean.domain.port.GeocodingGateway;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class GeocodePlaceQueryService {

    private static final String KEY_PREFIX = "place:";
    private static final Duration TTL = Duration.ofHours(1);

    private final GeocodingGateway gateway;
    private final LocationCache cache;

    public GeocodePlaceQueryService(GeocodingGateway gateway, LocationCache cache) {
        this.gateway = gateway;
        this.cache = cache;
    }

    public GeocodedLocation handle(String placeId) {
        if (placeId == null || placeId.isBlank()) {
            throw new IllegalArgumentException("placeId is required");
        }

        String id = placeId.trim();
        String key = KEY_PREFIX + id;

        Optional<GeocodedLocation> cached = cache.find(key, GeocodedLocation.class);
        if (cached.isPresent()) {
            return cached.get();
        }

        GeocodedLocation location = gateway.geocodePlace(id)
                .orElseThrow(() -> new LocationNotFoundException("Place not found: " + id));

        cache.put(key, location, TTL);
        return location;
    }
}