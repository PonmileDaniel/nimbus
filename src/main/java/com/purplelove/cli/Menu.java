package com.purplelove.cli;

import com.purplelove.model.User;
import com.purplelove.model.Post;
import com.purplelove.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Menu {

    private final HttpClient client = HttpClient.newHttpClient();
    private final JsonParser parser = new JsonParser();
    private final Scanner scanner = new Scanner(System.in);

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private void start() {
        System.out.println("=== Nimbus cli ===");
        System.out.println("A lightweight Http client with local caching (coming soon)")
        System.out.println();

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> fetchUsers();
                case "2" -> fetchPosts();
                case "3" -> {
                    System.out.println("Goodbye! ");
                    return;
                }
                default -> System.out.print("Invalid choice. Please enter 1, 2, or 3");
            }
            System.out.println();

        }
    }

    private void displayMenu() {
        System.out.println("Choose an option:");
        System.out.println(" 1. Fetch Users");
        System.out.println(" 2. Fetch Posts");
        System.out.println("  3. Exit");
        System.out.print("Choice: ");
    }
    
    private void fetchUsers() {
        System.out.print("\nLoading... ");
        String json = fetch("/users");
        if (json == null) return;

        try{
            List<User> users = parser.parseUsers(json);
            System.out.println("Found " + users.size() + " users:\n");
            users.forEach(user -> System.out.printf("  %d. %s (@%s)%n", user.id(), user.name(), user.username()));

        } catch(Exception e) {
            System.out.println("Failed to parse users: " + e.getMessage());

        }
    }

    private void fetchPosts() {
        System.out.print("\nLoading... ");
        String json = fetch("/posts")


    }
}
