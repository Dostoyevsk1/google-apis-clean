package com.trakto.googlemapsclean.interfaces.rest.assembler;

import com.trakto.googlemapsclean.domain.model.AddressVerdict;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.AddressValidateResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressValidationDtoAssembler {

    public AddressValidateResponse toAddressValidateResponseFromDomain(AddressVerdict verdict) {
        return new AddressValidateResponse(
                verdict.complete(),
                verdict.formattedAddress()
        );
    }
}