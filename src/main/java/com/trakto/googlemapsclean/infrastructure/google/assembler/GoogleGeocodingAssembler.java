package com.trakto.googlemapsclean.infrastructure.google.assembler;

import com.google.maps.geocode.v4.GeocodeAddressResponse;
import com.google.maps.geocode.v4.GeocodeResult;
import com.trakto.googlemapsclean.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class GoogleGeocodingAssembler {

    public GeocodingResult toResult(GeocodeAddressResponse response) {
        return new GeocodingResult(
                response.getResultsList().stream()
                        .map(this::toLocation)
                        .toList()
        );
    }

    public GeocodedLocation toLocation(GeocodeResult result) {
        return new GeocodedLocation(
                result.getFormattedAddress(),
                new GeoPoint(
                        result.getLocation().getLatitude(),
                        result.getLocation().getLongitude()
                ),
                result.getPlaceId(),
                toGranularity(result.getGranularity().name()),
                result.getTypesList()
        );
    }

    private Granularity toGranularity(String googleName) {
        try {
            return Granularity.valueOf(googleName);
        } catch (IllegalArgumentException e) {
            return Granularity.UNKNOWN;
        }
    }



}