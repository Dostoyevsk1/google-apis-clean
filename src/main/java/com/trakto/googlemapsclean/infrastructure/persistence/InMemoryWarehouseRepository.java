package com.trakto.googlemapsclean.infrastructure.persistence;

import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.Warehouse;
import com.trakto.googlemapsclean.domain.port.WarehouseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class InMemoryWarehouseRepository implements WarehouseRepository {

    // Reemplaza estos datos por los de tu MockSpatialData.WARE_HOUSES
    private static final List<Warehouse> WAREHOUSES = List.of(
            new Warehouse("W1", "Central",  new GeoPoint(12.9716, 77.5946)),
            new Warehouse("W2", "North",    new GeoPoint(13.0358, 77.5970)),
            new Warehouse("W3", "South",    new GeoPoint(12.9000, 77.5800)),
            new Warehouse("W4", "North",    new GeoPoint(13.0354, 77.5979)),
            new Warehouse("W5", "Central",  new GeoPoint(12.9002, 77.5801)),
            new Warehouse("W6", "Central",  new GeoPoint(12.9716, 77.5945))

    );

    @Override
    public List<Warehouse> findAll() {
        return WAREHOUSES;
    }
}