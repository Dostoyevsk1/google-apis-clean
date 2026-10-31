package com.trakto.googlemapsclean.domain.port;

import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.GeocodedLocation;

import java.util.Optional;

public interface ReverseGeocodingGateway {

    Optional<GeocodedLocation> reverseGeocode(GeoPoint point);
}