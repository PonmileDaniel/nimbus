package com.purplelove.cli;

import java.util.List;
import java.util.Scanner;
import com.purplelove.http.HttpService;
import com.purplelove.model.Post;
import com.purplelove.model.User;
import com.purplelove.parser.JsonParser;


public class Menu {
    private final HttpService httpService;
    private final JsonParser parser;
    private final Scanner scanner;

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public Menu() {
        this.httpService = new HttpService(BASE_URL);
        this.parser = new JsonParser();
        this.scanner = new Scanner(System.in);

    }

    public void start() {
        System.out.println("=== Nimbus cli ===");
        System.out.println("A lightweight Http client with local caching (coming soon)");
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
        System.out.println(" 3. Exit");
        System.out.print("Choice: ");
    }
    
    private void fetchUsers() {
        System.out.print("\nLoading... ");

        try{
            String json = httpService.get("/users");
            List<User> users = parser.parseUsers(json);
            System.out.println("Found " + users.size() + " users:\n");
            users.forEach(user -> System.out.printf("  %d. %s (@%s)%n", user.id(), user.name(), user.username()));

        } catch(Exception e) {
            System.out.println("Failed to parse users: " + e.getMessage());

        }
    }

    private void fetchPosts() {
        System.out.print("\nLoading... ");

        try {
            String json = httpService.get("/posts");
            List<Post> posts = parser.parsePosts(json);
            System.out.println("Found " + posts.size() + " posts:\n");
            posts.stream().limit(5).forEach(post -> 
                System.out.printf("  [Post %d] %s%n", post.id(), post.title())
            );
            if (posts.size() > 5) {
                System.out.printf("  ... and %d more%n", posts.size() - 5);
            }
        } catch (Exception e) {
             System.err.println("Failed to parse posts: " + e.getMessage());
        }
    }
}
