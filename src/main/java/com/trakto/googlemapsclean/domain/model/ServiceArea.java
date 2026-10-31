package com.trakto.googlemapsclean.domain.model;

import java.util.List;

public record ServiceArea(String name, List<GeoPoint> vertices) {

    public ServiceArea {
        if (vertices == null || vertices.size() < 3) {
            throw new IllegalArgumentException("A service area needs at least 3 vertices");
        }
        vertices = List.copyOf(vertices);
    }

    public ServiceAreaStatus evaluate(GeoPoint point) {
        return contains(point)
                ? ServiceAreaStatus.INSIDE_SERVICE_AREA
                : ServiceAreaStatus.OUTSIDE_SERVICE_AREA;
    }

    // Ray casting: x = lng, y = lat. Válido para áreas del tamaño de una ciudad.
    private boolean contains(GeoPoint point) {
        double x = point.lng(), y = point.lat();
        boolean inside = false;

        for (int i = 0, j = vertices.size() - 1; i < vertices.size(); j = i++) {
            double xi = vertices.get(i).lng(), yi = vertices.get(i).lat();
            double xj = vertices.get(j).lng(), yj = vertices.get(j).lat();

            boolean intersect = ((yi > y) != (yj > y))
                    && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) inside = !inside;
        }
        return inside;
    }
}