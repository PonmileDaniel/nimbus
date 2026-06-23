package com.purplelove.cache;

import java.time.Duration;

public enum CachePolicy {
    FOREVER(Duration.ofDays(365 * 10)),
    FIVE_MINUTES(Duration.ofMinutes(5)),
    ONE_HOUR(Duration.ofHours(1)),
    NO_CACHE(Duration.ZERO);

    private final Duration duration;

    CachePolicy(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns true if this policy should cache data.
     */
    public boolean shouldCache() {
        return this != NO_CACHE;
    }
}