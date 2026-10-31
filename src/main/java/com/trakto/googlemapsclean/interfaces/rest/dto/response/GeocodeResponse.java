package com.trakto.googlemapsclean.interfaces.rest.dto.response;

import java.util.List;

public record GeocodeResponse(
        int resultCount,
        boolean multipleCandidates,
        List<LocationResponse> results
) {
}