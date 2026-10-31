package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.application.exception.WarehouseNotAvailableException;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.WarehouseDistance;
import com.trakto.googlemapsclean.domain.port.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class FindNearestWarehouseQueryService {

    private final WarehouseRepository warehouseRepository;

    public FindNearestWarehouseQueryService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseDistance handle(GeoPoint customerLocation) {
        if (customerLocation == null) {
            throw new IllegalArgumentException("customerLocation is required");
        }

        return warehouseRepository.findAll().stream()
                .map(w -> new WarehouseDistance(w, w.location().distanceKmTo(customerLocation)))
                .min(Comparator.comparingDouble(WarehouseDistance::distanceKm))
                .orElseThrow(() -> new WarehouseNotAvailableException("No warehouses available"));
    }
}