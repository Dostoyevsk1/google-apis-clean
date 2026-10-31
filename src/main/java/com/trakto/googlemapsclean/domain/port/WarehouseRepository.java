package com.trakto.googlemapsclean.domain.port;

import com.trakto.googlemapsclean.domain.model.Warehouse;

import java.util.List;

public interface WarehouseRepository {
    List<Warehouse> findAll();
}