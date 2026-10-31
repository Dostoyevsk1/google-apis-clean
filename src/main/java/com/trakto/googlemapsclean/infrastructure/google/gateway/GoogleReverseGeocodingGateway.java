package com.trakto.googlemapsclean.infrastructure.google.gateway;

import com.google.api.gax.rpc.ApiException;
import com.google.maps.geocode.v4.GeocodeLocationRequest;
import com.google.maps.geocode.v4.GeocodeLocationResponse;
import com.google.maps.geocode.v4.GeocodeServiceClient;
import com.google.type.LatLng;
import com.trakto.googlemapsclean.domain.exception.UpstreamLocationException;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.GeocodedLocation;
import com.trakto.googlemapsclean.domain.port.ReverseGeocodingGateway;
import com.trakto.googlemapsclean.infrastructure.google.assembler.GoogleGeocodingAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class GoogleReverseGeocodingGateway implements ReverseGeocodingGateway {

    private final GeocodeServiceClient client;
    private final GoogleGeocodingAssembler assembler;

    GoogleReverseGeocodingGateway(GeocodeServiceClient client,
                                  GoogleGeocodingAssembler assembler) {
        this.client = client;
        this.assembler = assembler;
    }

    @Override
    public Optional<GeocodedLocation> reverseGeocode(GeoPoint point) {

        LatLng location = LatLng.newBuilder()
                .setLatitude(point.lat())
                .setLongitude(point.lng())
                .build();

        GeocodeLocationRequest request = GeocodeLocationRequest.newBuilder()
                .setLocation(location)
                .build();

        try {

            GeocodeLocationResponse response = client.geocodeLocation(request);

            if (response.getResultsCount() == 0) {
                return Optional.empty();
            }
            GeocodedLocation locationResult = assembler.toLocation(response.getResults(0));
            return Optional.of(locationResult);

        } catch (ApiException e) {
            throw new UpstreamLocationException("Reverse geocoding request failed", e);
        }
    }
}