# 🌤️ Nimbus

> A lightweight Java HTTP client built from scratch with caching, retry logic, JSON parsing, and a Spring-inspired API.

![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 📖 About Nimbus

Nimbus is an educational Java backend project designed to demonstrate how modern frameworks like Spring Boot handle HTTP communication, caching, retries, JSON serialization, and API abstraction under the hood.

Instead of relying on framework magic, Nimbus implements these features from first principles using Java's standard libraries and a few carefully chosen dependencies.

### Why I Built This

Most developers learn how to use frameworks before understanding what those frameworks actually do.

Nimbus was built to answer questions like:

- How does a REST client work internally?
- How does caching reduce network calls?
- How are retries implemented?
- How does JSON become Java objects?
- What does Spring's `RestTemplate`, `WebClient`, and `@Cacheable` really do?

By building these components manually, you gain a much deeper understanding of backend development.

---

## ✨ Features

### 🌐 HTTP Client
- Built using `java.net.http.HttpClient`
- Supports GET requests
- Request and response handling
- Status code validation

### 💾 Local File-Based Caching
- Automatic cache storage
- Cache expiration policies
- Reduces unnecessary API requests
- Cache hit/miss detection

### 🔄 Retry Logic
- Exponential backoff strategy
- Configurable retry attempts
- Handles temporary network failures gracefully

### 📊 JSON Parsing
- Jackson ObjectMapper integration
- Automatic mapping between JSON and Java Records
- Generic type-safe deserialization

### 📝 Structured Logging
- Timestamped logs
- Multiple log levels
- Easy debugging and monitoring

### 🎯 Type-Safe API
- Generic API methods
- Compile-time type checking
- Cleaner developer experience

### 🖥️ Interactive CLI
- Menu-driven interface
- Fetch data from multiple endpoints
- Demonstrates caching and retry behavior

---

## 🏗️ Architecture

```text
Nimbus
│
├── CLI Layer
│   └── Menu
│
├── API Layer
│   └── ApiClient
│
├── HTTP Layer
│   └── HttpService
│
├── Cache Layer
│   └── CacheManager
│
├── Retry Layer
│   └── RetryHandler
│
├── JSON Layer
│   └── JsonParser
│
├── Models
│   ├── User
│   ├── Post
│   ├── Comment
│   ├── Album
│   ├── Photo
│   └── Todo
│
└── Utilities
    └── Logger
```

---

## 📂 Project Structure

```text
src/main/java/com/purplelove/nimbus

├── Main.java
│
├── cli
│   └── Menu.java
│
├── http
│   ├── ApiClient.java
│   ├── HttpService.java
│   ├── HttpMethod.java
│   ├── ApiResponse.java
│   └── RetryHandler.java
│
├── cache
│   ├── CacheManager.java
│   ├── CacheEntry.java
│   └── CachePolicy.java
│
├── parser
│   └── JsonParser.java
│
├── model
│   ├── User.java
│   ├── Post.java
│   ├── Comment.java
│   ├── Album.java
│   ├── Photo.java
│   └── Todo.java
│
├── exception
│   ├── NimbusException.java
│   ├── ApiException.java
│   ├── CacheException.java
│   └── JsonException.java
│
└── util
    └── Logger.java
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+

### Clone Repository

```bash
git clone https://github.com/yourusername/nimbus.git
cd nimbus
```

### Build Project

```bash
mvn clean compile
```

### Run Application

```bash
mvn exec:java -Dexec.mainClass=com.purplelove.nimbus.Main
```

---

## 🖥️ Example Usage

### Create API Client

```java
ApiClient client = new ApiClient(
    "https://jsonplaceholder.typicode.com"
);
```

### Fetch a List

```java
List<User> users = client.getList(
    "/users",
    User.class
);
```

### Fetch a Single Resource

```java
User user = client.get(
    "/users/1",
    User.class
);
```

### Custom Cache Policy

```java
ApiClient client = new ApiClient(
    "https://jsonplaceholder.typicode.com",
    CachePolicy.ONE_HOUR
);
```

---

## 💾 Cache Policies

Nimbus supports multiple cache strategies:

| Policy | Description |
|----------|------------|
| FOREVER | Never expires |
| FIVE_MINUTES | Expires after 5 minutes |
| ONE_HOUR | Expires after 1 hour |
| NO_CACHE | Always fetch from network |

Example:

```java
CachePolicy.ONE_HOUR
```

---

## 🔄 Retry Strategy

Nimbus automatically retries failed requests using exponential backoff.

Example timing:

```text
Attempt 1 → Immediate
Attempt 2 → Wait 1 second
Attempt 3 → Wait 2 seconds
Attempt 4 → Wait 4 seconds
```

This improves resilience against temporary network issues.

---

## 📋 Sample Logs

```text
[INFO] Cache MISS for /users
[INFO] Fetching data from network...

[INFO] Cache HIT for /users

[WARN] Attempt 1 failed.
[WARN] Retrying in 1000ms...

[ERROR] Request failed after max retries.
```

---

## 🎓 Concepts Demonstrated

Nimbus covers many important Java backend concepts:

| Concept | Used In |
|----------|---------|
| OOP | Service architecture |
| Records | Models |
| Enums | Cache policies |
| Generics | API responses |
| Exception Handling | Custom exception hierarchy |
| File I/O | Cache storage |
| Java HTTP Client | Network communication |
| Jackson | JSON serialization |
| Dependency Injection | Constructor injection |
| Logging | Monitoring and debugging |

---

## 🧪 Future Improvements

- [ ] POST requests
- [ ] PUT requests
- [ ] DELETE requests
- [ ] Async requests using CompletableFuture
- [ ] Circuit Breaker pattern
- [ ] JUnit 5 test suite
- [ ] Annotation-based caching
- [ ] Multiple API base URLs
- [ ] Request interceptors
- [ ] Metrics and monitoring

---

## 📚 What I Learned

Building Nimbus helped me understand:

- HTTP communication at a lower level
- Caching strategies
- Retry mechanisms
- JSON serialization and deserialization
- Clean architecture principles
- Generic programming in Java
- How backend frameworks abstract complexity

---

## 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Open a Pull Request

---

## 📄 License

Licensed under the MIT License.

---

## 👨‍💻 Author

Built by **purpleLove**

Backend Developer | Java Enthusiast | Software Engineer

> "Understand the abstraction before relying on it."