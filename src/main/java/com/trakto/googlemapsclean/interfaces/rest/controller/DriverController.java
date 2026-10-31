package com.trakto.googlemapsclean.interfaces.rest.controller;

import com.trakto.googlemapsclean.application.query.FindNearbyDriversQueryService;
import com.trakto.googlemapsclean.interfaces.rest.assembler.SpatialDtoAssembler;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.NearbyDriverResponse;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final FindNearbyDriversQueryService findNearbyDrivers;
    private final SpatialDtoAssembler assembler;

    public DriverController(FindNearbyDriversQueryService findNearbyDrivers,
                            SpatialDtoAssembler assembler) {
        this.findNearbyDrivers = findNearbyDrivers;
        this.assembler = assembler;
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyDriverResponse>> nearby(
            @RequestParam @DecimalMin("-90")  @DecimalMax("90")  double lat,
            @RequestParam @DecimalMin("-180") @DecimalMax("180") double lng,
            @RequestParam @Positive @Max(50) double radiusKm) {

        var result = findNearbyDrivers.handle(assembler.toGeoPoint(lat, lng), radiusKm);
        return ResponseEntity.ok(assembler.toResponse(result));
    }
}