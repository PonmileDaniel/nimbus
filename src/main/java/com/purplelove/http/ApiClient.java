package com.purplelove.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purplelove.cache.CachePolicy;
import com.purplelove.parser.JsonParser;

import java.util.List;

public class ApiClient {

    private final HttpService httpService;
    private final JsonParser jsonParser;
    private final ObjectMapper objectMapper;

    public ApiClient(String baseUrl) {
        this.httpService = new HttpService(baseUrl);
        this.jsonParser = new JsonParser();
        this.objectMapper = new ObjectMapper();
    }

    public ApiClient(String baseUrl, CachePolicy cachePolicy) {
        this.httpService = new HttpService(baseUrl, cachePolicy);
        this.jsonParser = new JsonParser();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * GET request that returns a single object.
     * Example: User user = apiClient.get("/users/1", User.class);
     */
    public <T> T get(String endpoint, Class<T> responseType) {
        String json = httpService.get(endpoint);
        try {
            return objectMapper.readValue(json, responseType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON into " + responseType.getSimpleName(), e);
        }
    }

    /**
     * GET request that returns a list of objects.
     * Example: List<User> users = apiClient.getList("/users", User.class);
     */
    public <T> List<T> getList(String endpoint, Class<T> elementType) {
        String json = httpService.get(endpoint);
        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON into List<" + elementType.getSimpleName() + ">", e);
        }
    }

    // We'll add POST and DELETE in the future, but for now, GET is enough.
}