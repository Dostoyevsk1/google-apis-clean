package com.trakto.googlemapsclean.infrastructure.cache;

import com.trakto.googlemapsclean.application.port.LocationCache;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InMemoryLocationCache implements LocationCache {

    private static final int DEFAULT_MAX_ENTRIES = 10_000;

    private final Map<String, CacheEntry> store = new ConcurrentHashMap<>();
    private final Clock clock;
    private final int maxEntries;

    /*
    store = {
  "geocode:av. arequipa 123, lima"
        → CacheEntry(
              value     = GeocodingResult[
              GeocodedLocation("Av. Arequipa 123,
              Lima, Perú", GeoPoint(-12.0903, -77.0334),
              "ChIJabc123",
              ROOFTOP,
              ["street_address"]) ],
              expiresAt = 2026-10-29T18:30:00Z
          ),

  "place:ChIJabc123"
        → CacheEntry(
              value     = GeocodedLocation(
              "Av. Arequipa 123, Lima, Perú",
              GeoPoint(-12.0903, -77.0334),
              "ChIJabc123",
              ROOFTOP,
              ["street_address"]),
              expiresAt = 2026-10-29T18:45:00Z
          ),

  "reverse:-12.04637:-77.04279"
        → CacheEntry(
              value     = GeocodedLocation(
              "Plaza Mayor, Lima, Perú",
              GeoPoint(-12.04637, -77.04279),
              "ChIJxyz789",
              GEOMETRIC_CENTER,
               ["premise"]),
              expiresAt = 2026-10-29T19:00:00Z
          )
}
     */

    InMemoryLocationCache() {
        this(Clock.systemUTC(), DEFAULT_MAX_ENTRIES);
    }

    // Para tests
    InMemoryLocationCache(Clock clock, int maxEntries) {
        this.clock = clock;
        this.maxEntries = maxEntries;
    }

    @Override
    public <T> Optional<T> find(String key, Class<T> type) {

        CacheEntry entry = store.get(key);

        if (entry == null) {
            return Optional.empty();
        }
        if (entry.isExpired(Instant.now(clock))) {
            store.remove(key, entry);
            return Optional.empty();
        }
        if (!type.isInstance(entry.value())) {
            return Optional.empty();           // tipo distinto al pedido: se trata como fallo de caché
        }
        return Optional.of(type.cast(entry.value()));
    }

    @Override
    public <T> void put(String key, T value, Duration ttl) {

        if (ttl == null || ttl.isZero() || ttl.isNegative()) {
            throw new IllegalArgumentException("ttl must be positive");
        }
        if (store.size() >= maxEntries) {
            evict();
        }
        store.put(key, new CacheEntry(value, Instant.now(clock).plus(ttl)));
    }

    private void evict() {
        Instant now = Instant.now(clock);
        // entrySet = devuelve conjunto  del mapa pero no modifica ni elimina
        store.entrySet().removeIf(e -> e.getValue().isExpired(now));


        if (store.size() >= maxEntries) {
            store.clear();
        }
    }
}