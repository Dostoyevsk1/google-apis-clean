package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.application.exception.LocationNotFoundException;
import com.trakto.googlemapsclean.application.port.LocationCache;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.GeocodedLocation;
import com.trakto.googlemapsclean.domain.port.ReverseGeocodingGateway;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class ReverseGeocodeQueryService {

    private static final String KEY_PREFIX = "reverse:";
    private static final Duration TTL = Duration.ofHours(1);

    private final ReverseGeocodingGateway gateway;
    private final LocationCache cache;

    public ReverseGeocodeQueryService(ReverseGeocodingGateway gateway, LocationCache cache) {
        this.gateway = gateway;
        this.cache = cache;
    }

    public GeocodedLocation handle(GeoPoint rawPoint) {

        GeoPoint point = rawPoint.rounded();
        String key = KEY_PREFIX + point.lat() + ":" + point.lng();

        Optional<GeocodedLocation> cached = cache.find(key, GeocodedLocation.class);
        if (cached.isPresent()) {
            return cached.get();
        }

        GeocodedLocation location = gateway.reverseGeocode(point)
                .orElseThrow(() -> new LocationNotFoundException(
                        "No address found for " + point.lat() + ", " + point.lng()));

        cache.put(key, location, TTL);
        return location;
    }
}