# E-com: Spring Boot E-commerce API

A Java Spring Boot-based e-commerce backend with MongoDB, JWT authentication, and modular architecture.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (running locally or accessible remotely)

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/myselfavaneesh/E-com.git
   cd E-com
   ```

2. **Install MongoDB** and ensure it is running locally or update the connection string for remote access.

3. **Configure application properties**
   - Edit `src/main/resources/application.yml` to set your MongoDB connection details and JWT secret.

4. **Build and Run**
   - On Unix/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```
   - On Windows:
     ```cmd
     mvnw spring-boot:run
     ```

## API Overview

- `/auth/user` - User authentication and update endpoints
- `/product` - Product CRUD and listing endpoints
- `/categories` - Category CRUD endpoints
- `/cart` - Cart management endpoints

## Project Structure

- `controller/` - REST API controllers
- `services/` - Business logic and services
- `repository/` - MongoDB repositories
- `entities/` - Data models
- `dto/` - Data Transfer Objects
- `utils/` - Utility classes (e.g. JWT utility, API response wrapper)
- `config/` - Security configuration

## Running Tests

Run all tests with:
```bash
./mvnw test
```

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests.

## License

This project currently does not specify a license. Consider adding one (e.g., MIT, Apache 2.0) for open source use.
