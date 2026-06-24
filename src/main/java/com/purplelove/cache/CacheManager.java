package com.purplelove.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import com.purplelove.exception.CacheException;
import com.purplelove.utils.Logger;

public class CacheManager {
    private final Path cacheDir;
    private static final Logger logger = Logger.getLogger(CacheManager.class);

    public CacheManager() {
        // Created a folder called "nimbus-cache" in the project root
        this.cacheDir = Paths.get("nimbus-cache");

        try {
            if (!Files.exists(cacheDir)) {
                Files.createDirectories(cacheDir);
                logger.info("Created cache directory: " + cacheDir.toAbsolutePath());
            }

        } catch(IOException e) {
            throw new CacheException("Failed to created cache directory", e);

        }
    }

    /**
     * Save the JSON response for a given endpoint.
     * The filename is derived from the endpoint
     */
    public void save(String endpoint, String jsonBody) {
        Path filePath = getFilePath(endpoint);

        try {
            Files.writeString(filePath, jsonBody);
            logger.debug("Saved cache for " + endpoint);

        } catch (IOException e) {
            throw new CacheException("Failed to save cache for " + endpoint, e);

        }
    }

    /**
     * Retrieves the cached JSON for a given endpoint, if it exists.
     * Returns an empty Optional if the file does not exist or cannot be read.
     */

    public Optional<CacheEntry> get(String endpoint, CachePolicy policy) {
        if (policy == CachePolicy.NO_CACHE) {
            return Optional.empty();
        }

        Path filePath = getFilePath(endpoint);
        if(!Files.exists(filePath)) {
            return Optional.empty();
        }

        try {
            Instant fileTime = Files.getLastModifiedTime(filePath).toInstant();
            Instant now = Instant.now();

            Duration age = Duration.between(fileTime, now);

            if (age.compareTo(policy.getDuration()) > 0) {
                Files.deleteIfExists(filePath);
                logger.info("Cache expired for " + endpoint + " (age: " + age.toMinutes() + " min)");
                return Optional.empty();
            }
            String json = Files.readString(filePath);
            logger.info("Cache HIT for " + endpoint + " (age: " + age.toMinutes() + " min)");
            return Optional.of(new CacheEntry(json, fileTime));

        } catch (IOException e) {
            logger.warn("Failed to read cache for " + endpoint + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Delete  the cache file for a given endpoint 
     */
    public void invalidate(String endpoint) {
        Path filePath = getFilePath(endpoint);
        try {
            Files.deleteIfExists(filePath);
            logger.debug("Invalidated cache for " + endpoint);
        } catch (IOException e) {
            throw new CacheException("Failed to invalidate cache for " + endpoint, e);
        }
    }

    public Path getFilePath(String endpoint) {
        // Sanitize endpoint to a valid filename.
        // Remove leading slash and replace any other problematic chars with "_"
        String sanitized = endpoint.replaceAll("^/", "") // remove leading slash
                                   .replaceAll("[^a-zA-Z0-9.-]", "_");
        
        if (sanitized.isEmpty()) {
            sanitized = "root";
        }

        // Ensure .json extension
        if(!sanitized.endsWith(".json")) {
            sanitized = sanitized + ".json";
        }
        return cacheDir.resolve(sanitized);
    }
    
}
