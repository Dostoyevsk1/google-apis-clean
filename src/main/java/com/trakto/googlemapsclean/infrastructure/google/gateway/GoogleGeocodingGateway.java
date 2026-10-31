package com.trakto.googlemapsclean.infrastructure.google.gateway;

import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.maps.geocode.v4.*;
import com.trakto.googlemapsclean.domain.exception.UpstreamLocationException;
import com.trakto.googlemapsclean.domain.model.GeocodedLocation;
import com.trakto.googlemapsclean.domain.model.GeocodingResult;
import com.trakto.googlemapsclean.domain.port.GeocodingGateway;
import com.trakto.googlemapsclean.infrastructure.google.assembler.GoogleGeocodingAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class GoogleGeocodingGateway implements GeocodingGateway {

    private final GeocodeServiceClient client;
    private final GoogleGeocodingAssembler assembler;

    GoogleGeocodingGateway(GeocodeServiceClient client,
                           GoogleGeocodingAssembler assembler) {
        this.client = client;
        this.assembler = assembler;
    }

    @Override
    public GeocodingResult geocode(String address) {

        // Solicitud
        GeocodeAddressRequest request = GeocodeAddressRequest.newBuilder()
                .setAddressQuery(address)
                .build();
        try {
            GeocodeAddressResponse response = client.geocodeAddress(request);
            return assembler.toResult(response);
        } catch (ApiException e) {
            throw new UpstreamLocationException("Geocoding request failed", e);
        }
    }

    @Override
    public Optional<GeocodedLocation> geocodePlace(String placeId) {

        GeocodePlaceRequest request = GeocodePlaceRequest.newBuilder()
                .setPlace("places/" + placeId)
                .build();
        try {
            GeocodeResult response = client.geocodePlace(request);
            return Optional.of(assembler.toLocation(response));
        } catch (NotFoundException e) {
            return Optional.empty();
        } catch (ApiException e) {
            throw new UpstreamLocationException("Place geocoding request failed", e);
        }
    }
}