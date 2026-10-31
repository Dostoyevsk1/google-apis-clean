package com.trakto.googlemapsclean.infrastructure.persistence;

import com.trakto.googlemapsclean.domain.model.Driver;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.port.DriverRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class InMemoryDriverRepository implements DriverRepository {

    private static final List<Driver> DRIVERS = List.of(
            new Driver("D1", "Johan",  new GeoPoint(12.9721, 77.5933)),
            new Driver("D2", "Manuel", new GeoPoint(12.9615, 77.6100)),
            new Driver("D3", "Segura", new GeoPoint(13.0200, 77.5500)),
            new Driver("D4", "Johan",  new GeoPoint(12.9521, 77.5934)),
            new Driver("D5", "Manuel", new GeoPoint(12.9214, 77.6122)),
            new Driver("D6", "Segura", new GeoPoint(13.0254, 77.5751))
    );

    @Override
    public List<Driver> findAll() {
        return DRIVERS;
    }
}