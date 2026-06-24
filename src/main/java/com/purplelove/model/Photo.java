package com.purplelove.model;

public record Photo(
    int albumId,
    int id,
    String title,
    String url,
    String thumbnailUrl
) {}