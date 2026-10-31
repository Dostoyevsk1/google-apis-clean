package com.trakto.googlemapsclean.interfaces.rest.controller;

import com.trakto.googlemapsclean.application.query.ReverseGeocodeQueryService;
import com.trakto.googlemapsclean.interfaces.rest.assembler.LocationDtoAssembler;
import com.trakto.googlemapsclean.interfaces.rest.dto.request.ReverseGeocodingRequest;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.LocationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reverse-geocoding")
public class ReverseGeocodingController {

    private final ReverseGeocodeQueryService reverseGeocodeService;
    private final LocationDtoAssembler assembler;

    public ReverseGeocodingController(ReverseGeocodeQueryService reverseGeocodeService,
                                      LocationDtoAssembler assembler) {
        this.reverseGeocodeService = reverseGeocodeService;
        this.assembler = assembler;
    }

    @PostMapping("/reverse-geocode")
    public ResponseEntity<LocationResponse> reverseGeocode(
            @Valid @RequestBody ReverseGeocodingRequest request) {

        var point = assembler.toGeoPointFromRequest(request);
        var location = reverseGeocodeService.handle(point);
        return ResponseEntity.ok(assembler.toLocationResponseFromDomain(location));
    }
}