package com.trakto.googlemapsclean.application.query;

import com.trakto.googlemapsclean.application.exception.ServiceAreaNotConfiguredException;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.ServiceAreaStatus;
import com.trakto.googlemapsclean.domain.port.ServiceAreaRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidateServiceAreaQueryService {

    private final ServiceAreaRepository serviceAreaRepository;

    public ValidateServiceAreaQueryService(ServiceAreaRepository serviceAreaRepository) {
        this.serviceAreaRepository = serviceAreaRepository;
    }

    public ServiceAreaStatus handle(GeoPoint location) {
        if (location == null) {
            throw new IllegalArgumentException("location is required");
        }
        return serviceAreaRepository.findActive()
                .orElseThrow(() -> new ServiceAreaNotConfiguredException("No active service area configured"))
                .evaluate(location);
    }
}