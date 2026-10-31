package com.trakto.googlemapsclean.domain.port;

import com.trakto.googlemapsclean.domain.model.ServiceArea;

import java.util.Optional;

public interface ServiceAreaRepository {
    Optional<ServiceArea> findActive();
}