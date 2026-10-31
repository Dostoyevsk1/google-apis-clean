package com.trakto.googlemapsclean.interfaces.rest.assembler;

import com.trakto.googlemapsclean.domain.model.DriverDistance;
import com.trakto.googlemapsclean.domain.model.GeoPoint;
import com.trakto.googlemapsclean.domain.model.ServiceAreaStatus;
import com.trakto.googlemapsclean.domain.model.WarehouseDistance;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.NearbyDriverResponse;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.NearestWarehouseResponse;
import com.trakto.googlemapsclean.interfaces.rest.dto.response.ServiceAreaResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpatialDtoAssembler {

    public GeoPoint toGeoPoint(double lat, double lng) {
        return new GeoPoint(lat, lng);
    }

    public List<NearbyDriverResponse> toResponse(List<DriverDistance> drivers) {
        return drivers.stream().map(this::toResponse).toList();
    }

    public NearbyDriverResponse toResponse(DriverDistance dd) {
        return new NearbyDriverResponse(
                dd.driver().id(),
                dd.driver().name(),
                dd.driver().location().lat(),
                dd.driver().location().lng(),
                round2(dd.distanceKm())
        );
    }

    public ServiceAreaResponse toResponse(ServiceAreaStatus status) {
        boolean inside = status == ServiceAreaStatus.INSIDE_SERVICE_AREA;
        return new ServiceAreaResponse(
                inside,
                status.name(),
                inside ? "Service available" : "Location is outside the service area"
        );
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public NearestWarehouseResponse toResponse(WarehouseDistance wd) {
        return new NearestWarehouseResponse(
                wd.warehouse().id(),
                wd.warehouse().name(),
                wd.warehouse().location().lat(),
                wd.warehouse().location().lng(),
                round2(wd.distanceKm())
        );
    }
}