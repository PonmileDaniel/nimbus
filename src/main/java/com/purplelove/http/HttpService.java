package com.purplelove.http;

import com.purplelove.cache.CacheEntry;
import com.purplelove.cache.CacheManager;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;


public class HttpService {
    private final HttpClient client;
    private final String baseUrl;
    private final CacheManager cacheManager;

    public HttpService(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
        this.cacheManager = new CacheManager();
    }

    /**
     * Performs a GET request to the given endpoint
     * Return the raw JSON response body as a String
     * Throws a RuntimeException if the status code is not 200 or if a network error occurs
     */
    public String get(String endpoint) {
        Optional<CacheEntry> cached = cacheManager.get(endpoint);
        if (cached.isPresent()) {
            System.out.println("[Cache] HIT for " + endpoint + " (cached at " + cached.get().createdAt() + ")");
            return cached.get().jsonBody();
        }
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

            cacheManager.save(endpoint, response.body());
            System.out.println("[Cache] Saved response for " + endpoint);
            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Network error while fetching " + fullUrl, e);

        }
    }

}