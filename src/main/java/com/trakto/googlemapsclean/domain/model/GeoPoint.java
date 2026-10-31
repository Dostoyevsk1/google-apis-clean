package com.trakto.googlemapsclean.domain.model;

public record GeoPoint(double lat, double lng) {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public GeoPoint {
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90: " + lat);
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180: " + lng);
        }
    }

    public GeoPoint rounded() {
        return new GeoPoint(round5(lat), round5(lng));
    }

    private static double round5(double value) {
        return Math.round(value * 100_000d) / 100_000d;
    }

    public double distanceKmTo(GeoPoint other) {
        double lat1 = Math.toRadians(lat);
        double lat2 = Math.toRadians(other.lat);
        double dLat = lat2 - lat1;
        double dLng = Math.toRadians(other.lng - lng);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLng / 2), 2);
        a = Math.clamp(a, 0.0, 1.0);

        return EARTH_RADIUS_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}