package com.purplelove.model;

public record Todo(
    int userId,
    int id,
    String title,
    boolean completed
) {}
