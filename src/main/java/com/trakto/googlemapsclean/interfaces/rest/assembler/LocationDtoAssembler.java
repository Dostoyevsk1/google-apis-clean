package com.trakto.googlemapsclean.interfaces.rest.assembler;

import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.GeocodedLocation;
import com.trakto.googlemapsclean.domain.model.GeocodingResult;
import com.trakto.googlemapsclean.interfaces.rest.dto.request.ReverseGeocodingRequest;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.GeocodeResponse;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.LocationResponse;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoAssembler {

    // Reverse Geocoding
    public GeoPoint toGeoPointFromRequest(ReverseGeocodingRequest request) {
        return new GeoPoint(request.lat(), request.lng());
    }
    // Geocoding
    public GeocodeResponse toGeocodeResponseFromDomain(GeocodingResult result) {
        return new GeocodeResponse(
                result.count(),
                result.isAmbiguous(),
                result.candidates().stream()
                        .map(this::toLocationResponseFromDomain)
                        .toList()
        );
    }

    public LocationResponse toLocationResponseFromDomain(GeocodedLocation location) {
        return new LocationResponse(
                location.formattedAddress(),
                location.point().lat(),
                location.point().lng(),
                location.placeId(),
                location.granularity().name(),
                location.types()
        );
    }
}