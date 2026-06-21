package com.purplelove.cache;

import java.time.Instant;

public record CacheEntry (
    String jsonBody,
    Instant createdAt
) {}
