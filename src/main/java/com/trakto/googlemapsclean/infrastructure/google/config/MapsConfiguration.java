package com.trakto.googlemapsclean.infrastructure.google.config;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.maps.addressvalidation.v1.AddressValidationClient;
import com.google.maps.addressvalidation.v1.AddressValidationSettings;
import com.google.maps.geocode.v4.GeocodeServiceClient;
import com.google.maps.geocode.v4.GeocodeServiceSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
class MapsConfiguration {

    private static final String API_KEY_HEADER = "x-goog-api-key";

    private final String mapsApiKey;

    MapsConfiguration(@Value("${maps.api.key:}") String mapsApiKey) {

        if (mapsApiKey == null || mapsApiKey.isBlank()) {
            throw new IllegalStateException("MAPS_API_KEY not set. Set env var MAPS_API_KEY before running.");
        }
        this.mapsApiKey = mapsApiKey;
    }

    @Bean
    AddressValidationClient addressValidationClient() throws IOException {
        AddressValidationSettings settings = AddressValidationSettings.newBuilder()
                .setHeaderProvider(FixedHeaderProvider.create(Map.of(API_KEY_HEADER, mapsApiKey))) // Api Key
                .setCredentialsProvider(NoCredentialsProvider.create()) // Excluye credenciales OAuth/ADC
                .build();
        return AddressValidationClient.create(settings);
    }

    @Bean
    GeocodeServiceClient geocodeServiceClient() throws IOException {
        GeocodeServiceSettings settings = GeocodeServiceSettings.newBuilder()
                .setHeaderProvider(FixedHeaderProvider.create(Map.of(API_KEY_HEADER, mapsApiKey)))
                .setCredentialsProvider(NoCredentialsProvider.create())
                .build();
        return GeocodeServiceClient.create(settings);
    }
}