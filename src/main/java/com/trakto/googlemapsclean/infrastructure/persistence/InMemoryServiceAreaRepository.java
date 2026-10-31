package com.trakto.googlemapsclean.infrastructure.persistence;

import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.ServiceArea;
import com.trakto.googlemapsclean.domain.port.ServiceAreaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class InMemoryServiceAreaRepository implements ServiceAreaRepository {

    private static final ServiceArea CITY = new ServiceArea("City", List.of(
            new GeoPoint(12.99, 77.55),
            new GeoPoint(13.05, 77.60),
            new GeoPoint(12.98, 77.65),
            new GeoPoint(12.90, 77.64),
            new GeoPoint(12.88, 77.57)
    ));

    @Override
    public Optional<ServiceArea> findActive() {
        return Optional.of(CITY);
    }
}