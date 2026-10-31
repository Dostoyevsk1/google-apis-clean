package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.application.port.LocationCache;
import com.trakto.googlemapsclean.domain.model.GeocodingResult;
import com.trakto.googlemapsclean.domain.port.GeocodingGateway;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class GeocodeAddressQueryService {

    private static final String KEY_PREFIX = "geocode:";
    private static final Duration TTL = Duration.ofHours(1);

    private final GeocodingGateway gateway;
    private final LocationCache cache;

    public GeocodeAddressQueryService(GeocodingGateway gateway,
                                      LocationCache cache) {
        this.gateway = gateway;
        this.cache = cache;
    }

    public GeocodingResult handle(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address is required");
        }

        String key = KEY_PREFIX + address.trim().toLowerCase();

        Optional<GeocodingResult> cached = cache.find(key, GeocodingResult.class);
        if(cached.isPresent()){
            return cached.get();
        }

        GeocodingResult result = gateway.geocode(address.trim());

        if(!result.isEmpty()){
            cache.put(key, result, TTL);
        }

        return result;

    }
}