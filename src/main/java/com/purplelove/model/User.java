package com.purplelove.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record User (
    int id,
    String name,
    String username,
    String email
) {}
