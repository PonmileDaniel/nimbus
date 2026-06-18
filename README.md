Milestone 1: Raw HTTP Call (Phase 1 - Part 1)
Goal: Prove we can talk to the internet. No models, no parsing. Just raw text.

In Main.java, write a method that uses java.net.http.HttpClient.

Build a GET request to https://jsonplaceholder.typicode.com/users.

Send the request and print the raw JSON response to the console.

Run it and verify you see a JSON array of 10 users.

Milestone 2: Models & JSON Parsing (Phase 1 - Part 2)
Goal: Turn that raw JSON into actual Java objects (Records).

Create a User record in the model package (int id, String name, String username, String email).

Create a Post record in the model package (int id, int userId, String title, String body).

Create a JsonParser class in the parser package.

Give JsonParser a Jackson ObjectMapper.

Write a method: List<User> parseUsers(String json).

Update Main to parse the JSON and print user.name() instead of the raw JSON.

Milestone 3: The CLI Menu (Phase 1 - Part 3)
Goal: Let the user choose what to fetch instead of hardcoding.

Create cli/Menu.java with a start() method.

Use a Scanner to read user input.

Print: 1. Fetch Users | 2. Fetch Posts | 3. Exit.

Loop until the user selects Exit.

Based on choice, call either fetchUsers() or fetchPosts() from Main.

Milestone 4: Separation of Concerns (The "HttpService")
Goal: Stop putting HTTP logic inside Main. Build the first piece of our framework.

Create http/HttpService.java.

Move the HttpClient instance into this class.

Create a method: String get(String url) that returns the raw JSON string.

Refactor Main to use HttpService instead of building requests itself.

Add basic error handling (if status code != 200, print an error).

Milestone 5: The Local Cache (Phase 2)
Goal: Save JSON responses to the hard drive so we don't call the API twice.

Create cache/CacheEntry.java (contains String jsonBody, Instant createdAt, String url).

Create util/FileUtils.java (methods to write a string to a file, read a string from a file, and check if a file exists).

Create cache/CacheManager.java (initializes a ./nimbus-cache/ folder).

Write save(String url, String json) that writes a .json file to disk.

Write Optional<String> get(String url) that reads the file if it exists.

Integrate: Before calling the API, check the cache. If found, use it. If not, call API and save the result.

Milestone 6: Cache Expiration & Policies (Phase 5)
Goal: Make the cache smart. Don't serve old data forever.

Create cache/CachePolicy.java as an enum (FOREVER, FIVE_MINUTES, ONE_HOUR, NO_CACHE).

Add a cachePolicy field to CacheEntry.

Update CacheManager.get(): before returning the JSON, check CacheEntry.createdAt against Instant.now().

If the duration has passed, delete the file and return Optional.empty() (forcing a new API call).

Hardcode the policy to FIVE_MINUTES for now to test it (fetch, wait 5 mins, fetch again—or mock the clock).

Milestone 7: Retry Logic (Phase 4)
Goal: Make the network resilient. If it fails, try again.

Create http/RetryHandler.java.

Write a method: String getWithRetry(String url, int maxAttempts).

Inside, loop attempt = 1 to maxAttempts.

Try to execute the HTTP call. If it succeeds, return the result.

If it fails (IOException or 5xx status), wait attempt * 1000 ms (exponential backoff) and try again.

Only throw an exception if all attempts fail.

Integrate this into HttpService so every request uses retries by default (e.g., 3 attempts).

Milestone 8: Build the "API Client" Framework (Phase 3)
Goal: Stop writing raw URLs. Create the RestTemplate/WebClient style API.

Create http/HttpMethod.java enum (GET, POST, DELETE, PUT).

Create http/ApiResponse.java (holds int statusCode, String body, boolean isSuccess).

Create http/ApiClient.java. This will be our main entry point.

Write a generic send(HttpMethod method, String endpoint, String body) method.

Write convenience methods: get(String endpoint), post(String endpoint, String body), delete(String endpoint).

Big Refactor: Change HttpService to use ApiClient internally. Now your Main can do apiClient.get("/users") instead of handling URLs manually. (Note: The base URL will be a constant in util/Constants.java).

Milestone 9: Custom Exceptions & Logging
Goal: Stop using System.out.println everywhere and handle failures properly.

Create exception/ApiException.java (checked or unchecked? We'll use unchecked).

Create exception/CacheException.java.

Create exception/JsonException.java.

Update HttpService, CacheManager, and JsonParser to throw these specific exceptions instead of generic ones.

Create util/Logger.java. Write a simple info(), warn(), and error() method that prints with timestamps (java.time).

Replace all System.out.println in the core classes (http, cache, parser) with this custom logger.

Milestone 10: Final Polish & Advanced Models
Goal: Make it a complete showcase of everything Java has to offer.

Add the remaining models: Comment.java, Album.java, Photo.java, Todo.java.

Update the CLI menu to allow fetching all endpoints from JSONPlaceholder.

In Main, display the results beautifully (e.g., print "User 1: Leanne Graham" instead of the whole JSON).

Ensure the CachePolicy can be configured per endpoint (e.g., cache Users for 1 hour, but Todos for 5 minutes).

Bonus / Stretch Goal (Optional)
Unit Testing: Add JUnit 5 to pom.xml. Write a test for CacheManager (using java.nio.file.Files to create temporary directories) and a test for RetryHandler (using a mocked HttpClient).

Concurrency: Make the ApiClient thread-safe (since HttpClient is already thread-safe, just ensure your CacheManager handles concurrent file writes properly using FileLock or Atomic operations).

