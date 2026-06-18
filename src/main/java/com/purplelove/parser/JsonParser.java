package com.purplelove.parser;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purplelove.model.User;
import com.purplelove.model.Post;
import java.util.List;


public class JsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> parseUsers(String json) throws Exception {
        return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
    }

    public List<Post> parsePosts(String json) throws Exception {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Post.class));

    }
}