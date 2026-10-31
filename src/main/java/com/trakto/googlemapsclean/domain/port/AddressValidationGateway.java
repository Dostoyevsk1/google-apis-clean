package com.trakto.googlemapsclean.domain.port;

import com.trakto.googlemapsclean.domain.model.AddressVerdict;

public interface AddressValidationGateway {

    AddressVerdict validate(String rawAddress);
}