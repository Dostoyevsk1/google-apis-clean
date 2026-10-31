package com.trakto.googlemapsclean.interfaces.rest.dto.response;

public record AddressValidateResponse(
        boolean complete,
        String formattedAddress
) {
}