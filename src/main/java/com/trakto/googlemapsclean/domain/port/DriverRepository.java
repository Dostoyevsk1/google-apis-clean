package com.trakto.googlemapsclean.domain.port;

import com.trakto.googlemapsclean.domain.model.Driver;

import java.util.List;

public interface DriverRepository {
    List<Driver> findAll();
}