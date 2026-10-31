package com.trakto.googlemapsclean.infrastructure.google.assembler;

import com.google.maps.addressvalidation.v1.ValidationResult;
import com.trakto.googlemapsclean.domain.model.*;
import org.springframework.stereotype.Component;

@Component
public class GoogleAddressValidationAssembler {

    public AddressVerdict toVerdict(ValidationResult result) {
        return new AddressVerdict(
                result.getVerdict().getAddressComplete(),
                result.getAddress().getFormattedAddress()
        );
    }


}