package com.trakto.googlemapsclean.interfaces.rest.dto.response;

import java.util.List;

public record LocationResponse(
        String formattedAddress,
        double latitude,
        double longitude,
        String placeId,
        String granularity,
        List<String> types
) {
}