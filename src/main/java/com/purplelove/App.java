package com.purplelove;

import com.purplelove.parser.JsonParser;
import com.purplelove.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.List;

public class App {
    private static final JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) {
        // 1. Create an HttpCLient instance (default configuration)
        HttpClient client = HttpClient.newHttpClient();

        // 2. Build a GET request to the JSONPlaceholder API
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/users"))
                .GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
             System.out.println("Status Code: " + response.statusCode());
             List<User> users = jsonParser.parseUsers(response.body());

             users.forEach(user -> 
                System.out.println("  " + user.id() + ". " + user.name() + " (@ " + user.username() + ")")
            );

             // Print the raw JSON response body
             System.out.println("Raw JSON Response:");
             System.out.println(response.body());
             

        } catch(IOException | InterruptedException e) {
            // If something goes wrong (network down, URL wrong, etc.), print the error
            System.err.println("Network error: " + e.getMessage());
            // e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
        }
    }
    
}