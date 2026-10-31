package com.trakto.googlemapsclean.application.port;

import java.time.Duration;
import java.util.Optional;

public interface LocationCache {

    /** Devuelve el valor si existe y no ha expirado. */
    <T> Optional<T> find(String key, Class<T> type);

    /** Guarda el valor durante el TTL indicado. */
    <T> void put(String key, T value, Duration ttl);
}