package com.purplelove.parser;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purplelove.model.Album;
import com.purplelove.model.Comment;
import com.purplelove.model.Photo;
import com.purplelove.model.Post;
import com.purplelove.model.Todo;
import com.purplelove.model.User;



public class JsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> parseUsers(String json) throws Exception {
        return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
    }
    public List<Post> parsePosts(String json) throws Exception {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Post.class));
    }

    public List<Comment> parseComments(String json) throws Exception {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Comment.class));
    }

    public List<Album> parseAlbums(String json) throws Exception {
        return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Album.class));
    }

    public List<Photo> parsePhotos(String json) throws Exception {
        return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Photo.class));
    }

    public List<Todo> parseTodos(String json) throws Exception {
        return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Todo.class));
    }
}