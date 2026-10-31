package com.trakto.googlemapsclean.interfaces.rest.controller;

import com.trakto.googlemapsclean.application.query.FindNearestWarehouseQueryService;
import com.trakto.googlemapsclean.interfaces.rest.assembler.SpatialDtoAssembler;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.NearestWarehouseResponse;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final FindNearestWarehouseQueryService findNearestWarehouse;
    private final SpatialDtoAssembler assembler;

    public WarehouseController(FindNearestWarehouseQueryService findNearestWarehouse,
                               SpatialDtoAssembler assembler) {
        this.findNearestWarehouse = findNearestWarehouse;
        this.assembler = assembler;
    }

    @GetMapping("/nearest")
    public ResponseEntity<NearestWarehouseResponse> nearest(
            @RequestParam @DecimalMin("-90")  @DecimalMax("90")  double lat,
            @RequestParam @DecimalMin("-180") @DecimalMax("180") double lng) {

        var result = findNearestWarehouse.handle(assembler.toGeoPoint(lat, lng));
        return ResponseEntity.ok(assembler.toResponse(result));
    }
}