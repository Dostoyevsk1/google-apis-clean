package com.trakto.googlemapsclean.domain.port;

import com.trakto.googlemapsclean.domain.model.GeocodedLocation;
import com.trakto.googlemapsclean.domain.model.GeocodingResult;

import java.util.Optional;

public interface GeocodingGateway {

    GeocodingResult geocode(String address);

    Optional<GeocodedLocation> geocodePlace(String placeId);
}