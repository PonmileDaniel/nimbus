package com.purplelove.cli;

import java.util.List;
import java.util.Scanner;

import com.purplelove.cache.CachePolicy;
import com.purplelove.http.ApiClient;
import com.purplelove.model.Album;
import com.purplelove.model.Comment;
import com.purplelove.model.Photo;
import com.purplelove.model.Post;
import com.purplelove.model.Todo;
import com.purplelove.model.User;
import com.purplelove.utils.Logger;

public class Menu {

    private final ApiClient apiClient;
    private final Scanner scanner;
    private static final Logger logger = Logger.getLogger(Menu.class);

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public Menu() {
        this.apiClient = new ApiClient(BASE_URL, CachePolicy.FIVE_MINUTES);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║           🌤️  NIMBUS CLI              ║");
        System.out.println("║    Lightweight HTTP Client with       ║");
        System.out.println("║        Local Caching & Retries        ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> fetchUsers();
                case "2" -> fetchPosts();
                case "3" -> fetchComments();
                case "4" -> fetchAlbums();
                case "5" -> fetchPhotos();
                case "6" -> fetchTodos();
                case "7" -> {
                    System.out.println("\n👋 Goodbye! Thanks for using Nimbus.");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Please enter 1-7.");
            }
            System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│  Choose an option:                  │");
        System.out.println("│   1. 👤 Fetch Users                 │");
        System.out.println("│   2. 📝 Fetch Posts                 │");
        System.out.println("│   3. 💬 Fetch Comments              │");
        System.out.println("│   4. 🎵 Fetch Albums                │");
        System.out.println("│   5. 📸 Fetch Photos                │");
        System.out.println("│   6. ✅ Fetch Todos                 │");
        System.out.println("│   7. 🚪 Exit                        │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Choice: ");
    }

    private void fetchUsers() {
        System.out.print("\n⏳ Loading users... ");
        try {
            List<User> users = apiClient.getList("/users", User.class);
            System.out.println("✅ Found " + users.size() + " users:\n");
            
            // Display in a nice table-like format
            System.out.println("┌────┬──────────────────────┬──────────────────────┐");
            System.out.println("│ ID │ Name                 │ Username             │");
            System.out.println("├────┼──────────────────────┼──────────────────────┤");
            users.forEach(user -> 
                System.out.printf("│ %2d │ %-20s │ %-20s │%n", 
                    user.id(), 
                    truncate(user.name(), 20), 
                    truncate(user.username(), 20))
            );
            System.out.println("└────┴──────────────────────┴──────────────────────┘");
        } catch (Exception e) {
            logger.error("Failed to fetch users", e);
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    private void fetchPosts() {
        System.out.print("\n⏳ Loading posts... ");
        try {
            List<Post> posts = apiClient.getList("/posts", Post.class);
            System.out.println("✅ Found " + posts.size() + " posts:\n");
            
            posts.stream().limit(10).forEach(post -> 
                System.out.printf("  📌 [Post %d] %s%n", post.id(), truncate(post.title(), 60))
            );
            if (posts.size() > 10) {
                System.out.printf("  ... and %d more posts%n", posts.size() - 10);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch posts", e);
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    private void fetchComments() {
        System.out.print("\n⏳ Loading comments... ");
        try {
            List<Comment> comments = apiClient.getList("/comments", Comment.class);
            System.out.println("✅ Found " + comments.size() + " comments:\n");
            
            comments.stream().limit(5).forEach(comment -> 
                System.out.printf("  💬 [%s] %s%n    by %s%n", 
                    truncate(comment.name(), 30), 
                    truncate(comment.body(), 50),
                    comment.email())
            );
            if (comments.size() > 5) {
                System.out.printf("  ... and %d more comments%n", comments.size() - 5);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch comments", e);
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    private void fetchAlbums() {
        System.out.print("\n⏳ Loading albums... ");
        try {
            List<Album> albums = apiClient.getList("/albums", Album.class);
            System.out.println("✅ Found " + albums.size() + " albums:\n");
            
            albums.stream().limit(10).forEach(album -> 
                System.out.printf("  🎵 [Album %d] %s (User %d)%n", 
                    album.id(), truncate(album.title(), 50), album.userId())
            );
            if (albums.size() > 10) {
                System.out.printf("  ... and %d more albums%n", albums.size() - 10);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch albums", e);
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    private void fetchPhotos() {
        System.out.print("\n⏳ Loading photos... ");
        try {
            List<Photo> photos = apiClient.getList("/photos", Photo.class);
            System.out.println("✅ Found " + photos.size() + " photos:\n");
            
            photos.stream().limit(5).forEach(photo -> 
                System.out.printf("  📸 [Photo %d] %s%n    %s%n", 
                    photo.id(), truncate(photo.title(), 50), photo.url())
            );
            if (photos.size() > 5) {
                System.out.printf("  ... and %d more photos%n", photos.size() - 5);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch photos", e);
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    private void fetchTodos() {
        System.out.print("\n⏳ Loading todos... ");
        try {
            List<Todo> todos = apiClient.getList("/todos", Todo.class);
            System.out.println("✅ Found " + todos.size() + " todos:\n");
            
            todos.stream().limit(10).forEach(todo -> 
                System.out.printf("  %s [Todo %d] %s%n", 
                    todo.completed() ? "✅" : "⬜", 
                    todo.id(), 
                    truncate(todo.title(), 50))
            );
            if (todos.size() > 10) {
                System.out.printf("  ... and %d more todos%n", todos.size() - 10);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch todos", e);
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    // Helper method to truncate long strings
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}