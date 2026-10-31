package com.trakto.googlemapsclean.interfaces.rest.controller;

import com.trakto.googlemapsclean.application.query.GeocodeAddressQueryService;
import com.trakto.googlemapsclean.application.query.GeocodePlaceQueryService;
import com.trakto.googlemapsclean.interfaces.rest.assembler.LocationDtoAssembler;
import com.trakto.googlemapsclean.interfaces.rest.dto.request.GeocodeRequest;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.GeocodeResponse;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.LocationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/geocoding")
public class GeocodingController {

    private final GeocodeAddressQueryService geocodeAddressService;
    private final GeocodePlaceQueryService geocodePlaceService;
    private final LocationDtoAssembler assembler;

    public GeocodingController(GeocodeAddressQueryService geocodeAddressService,
                               GeocodePlaceQueryService geocodePlaceService,
                               LocationDtoAssembler assembler) {
        this.geocodeAddressService = geocodeAddressService;
        this.geocodePlaceService = geocodePlaceService;
        this.assembler = assembler;
    }

    @GetMapping("/geocode")
    public ResponseEntity<GeocodeResponse> geocodeByQuery(
            @RequestParam @NotBlank @Size(max = 250) String address) {

        var result = geocodeAddressService.handle(address);
        return ResponseEntity.ok(assembler.toGeocodeResponseFromDomain(result));
    }

    @PostMapping("/geocode")
    public ResponseEntity<GeocodeResponse> geocode(@Valid @RequestBody GeocodeRequest request) {

        var result = geocodeAddressService.handle(request.address());
        return ResponseEntity.ok(assembler.toGeocodeResponseFromDomain(result));
    }

    @GetMapping("/geocode-place/{placeId}")
    public ResponseEntity<LocationResponse> geocodePlace(@PathVariable @NotBlank String placeId) {

        var location = geocodePlaceService.handle(placeId);
        return ResponseEntity.ok(assembler.toLocationResponseFromDomain(location));
    }
}