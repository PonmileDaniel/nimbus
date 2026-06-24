package com.purplelove.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import com.purplelove.cache.CacheEntry;
import com.purplelove.cache.CacheManager;
import com.purplelove.cache.CachePolicy;
import com.purplelove.exception.ApiException;
import com.purplelove.utils.Logger;

public class HttpService {
    private final HttpClient client;
    private final String baseUrl;
    private final CacheManager cacheManager;
    private final CachePolicy cachePolicy;
    private final RetryHandler retryHandler;
    private static final Logger logger = Logger.getLogger(HttpService.class);  

    public HttpService(String baseUrl) {
        this(baseUrl, CachePolicy.FIVE_MINUTES);
    }

    public HttpService(String baseUrl, CachePolicy cachePolicy) {
        this.client = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
        this.cacheManager = new CacheManager();
        this.cachePolicy = cachePolicy;

        // Default retry handler: 3 attempts, Starting at 1 second, max 10 Seconds
        this.retryHandler = new RetryHandler();
    }

    /**
     * Performs a GET request to the given endpoint
     * Return the raw JSON response body as a String
     * Throws a RuntimeException if the status code is not 200 or if a network error occurs
     */
    public String get(String endpoint) {
        Optional<CacheEntry> cached = cacheManager.get(endpoint, cachePolicy);
        if (cached.isPresent()) {
            System.out.println("[Cache] HIT for " + endpoint + " (cached at " + cached.get().createdAt() + ")");
            return cached.get().jsonBody();
        }

        logger.info("Cache MISS for " + endpoint + " – fetching from network...");
        String json = retryHandler.executeWithRetry(() -> {
            String fullUrl = baseUrl + endpoint;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("HTTP " + response.statusCode() + " from " + fullUrl);
                    }
                    return response.body();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException("Network error while fetching " + fullUrl, e);
                }
            });

            if (cachePolicy.shouldCache()) {
                cacheManager.save(endpoint, json);
                logger.info("Saved response for " + endpoint);
            }
            return json;
        }

}