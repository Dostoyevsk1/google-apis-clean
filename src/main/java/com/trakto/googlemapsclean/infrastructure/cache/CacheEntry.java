package com.trakto.googlemapsclean.infrastructure.cache;

import java.time.Instant;

record CacheEntry(Object value, Instant expiresAt) {

    boolean isExpired(Instant now) {
        return !now.isBefore(expiresAt);
    }
}