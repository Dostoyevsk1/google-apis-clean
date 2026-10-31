package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.domain.model.DriverDistance;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.port.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FindNearbyDriversQueryService {

    private final DriverRepository driverRepository;

    public FindNearbyDriversQueryService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<DriverDistance> handle(GeoPoint pickup, double radiusKm) {
        if (pickup == null) {
            throw new IllegalArgumentException("pickup is required");
        }
        if (radiusKm <= 0) {
            throw new IllegalArgumentException("radiusKm must be greater than 0");
        }

        return driverRepository.findAll().stream()
                .map(d -> new DriverDistance(d, d.location().distanceKmTo(pickup)))
                .filter(dd -> dd.distanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(DriverDistance::distanceKm))
                .toList();
    }
}