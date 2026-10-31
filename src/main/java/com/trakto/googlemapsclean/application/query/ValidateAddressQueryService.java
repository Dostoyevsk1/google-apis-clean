package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.domain.model.AddressVerdict;
import com.trakto.googlemapsclean.domain.port.AddressValidationGateway;
import org.springframework.stereotype.Service;

@Service
public class ValidateAddressQueryService {

    private final AddressValidationGateway gateway;

    public ValidateAddressQueryService(AddressValidationGateway gateway) {
        this.gateway = gateway;
    }

    public AddressVerdict handle(String rawAddress) {
        if (rawAddress == null || rawAddress.isBlank()) {
            throw new IllegalArgumentException("address is required");
        }
        return gateway.validate(rawAddress.trim());
    }
}