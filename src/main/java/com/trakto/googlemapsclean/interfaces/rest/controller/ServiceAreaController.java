package com.trakto.googlemapsclean.interfaces.rest.controller;

import com.trakto.googlemapsclean.application.query.ValidateServiceAreaQueryService;
import com.trakto.googlemapsclean.interfaces.rest.assembler.SpatialDtoAssembler;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.ServiceAreaResponse;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/service-area")
public class ServiceAreaController {

    private final ValidateServiceAreaQueryService validateServiceArea;
    private final SpatialDtoAssembler assembler;

    public ServiceAreaController(ValidateServiceAreaQueryService validateServiceArea,
                                 SpatialDtoAssembler assembler) {
        this.validateServiceArea = validateServiceArea;
        this.assembler = assembler;
    }

    @GetMapping("/check")
    public ResponseEntity<ServiceAreaResponse> check(
            @RequestParam @DecimalMin("-90")  @DecimalMax("90")  double lat,
            @RequestParam @DecimalMin("-180") @DecimalMax("180") double lng) {

        var status = validateServiceArea.handle(assembler.toGeoPoint(lat, lng));
        return ResponseEntity.ok(assembler.toResponse(status));
    }
}