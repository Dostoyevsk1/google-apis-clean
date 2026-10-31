package com.trakto.googlemapsclean.infrastructure.google.gateway;

import com.google.api.gax.rpc.ApiException;
import com.google.maps.addressvalidation.v1.AddressValidationClient;
import com.google.maps.addressvalidation.v1.ValidateAddressRequest;
import com.google.maps.addressvalidation.v1.ValidateAddressResponse;
import com.google.type.PostalAddress;
import com.trakto.googlemapsclean.domain.exception.UpstreamLocationException;
import com.trakto.googlemapsclean.domain.model.AddressVerdict;
import com.trakto.googlemapsclean.domain.port.AddressValidationGateway;
import com.trakto.googlemapsclean.infrastructure.google.assembler.GoogleAddressValidationAssembler;
import org.springframework.stereotype.Component;

@Component
class GoogleAddressValidationGateway implements AddressValidationGateway {

    private final AddressValidationClient client;
    private final GoogleAddressValidationAssembler assembler;

    GoogleAddressValidationGateway(AddressValidationClient client,
                                   GoogleAddressValidationAssembler assembler) {
        this.client = client;
        this.assembler = assembler;
    }

    @Override
    public AddressVerdict validate(String rawAddress) {

        if (rawAddress == null || rawAddress.isBlank()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        // Enviamos la direccion
        PostalAddress postalAddress = PostalAddress.newBuilder()
                .addAddressLines(rawAddress)
                .build();

        // Solicitud completa
        ValidateAddressRequest request = ValidateAddressRequest.newBuilder()
                .setAddress(postalAddress)
                .build();

        try {
            ValidateAddressResponse response = client.validateAddress(request);
            return assembler.toVerdict(response.getResult());
        } catch (ApiException e) {
            throw new UpstreamLocationException("Address validation request failed", e);
        }
    }
}