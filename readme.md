# E-com

E-com is an e-commerce backend application built using Java and Spring Boot. It provides APIs and services for managing products, categories, and users, with secure authentication and authorization. The project follows modern practices for building scalable and maintainable applications, leveraging MongoDB as the database.

## Features

- **User Management**
  - Create, update, and authenticate users
  - Password encryption and JWT-based authentication
  - Secure endpoints for user operations

- **Product Management**
  - Add, update, delete, and fetch products
  - Filter products by category and price range
  - Entity mapping and DTOs for clean data handling

- **Category Management**
  - CRUD operations for product categories
  - Category-based product filtering

- **Security**
  - Spring Security integration
  - JWT-based stateless authentication
  - Role-based access for endpoints

## Technologies Used

- Java
- Spring Boot
- Spring Data MongoDB
- Spring Security (with JWT)
- JUnit for testing
- Lombok
- MongoDB

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/myselfavaneesh/E-com.git
   ```

2. **Install MongoDB** and ensure it is running locally.

3. **Configure application properties**
   - Add your MongoDB connection details and JWT secret to `application.properties`.

4. **Build and Run**
   ```bash
   ./mvnw spring-boot:run
   ```

## API Overview

- `/auth/user` - User authentication and update endpoints
- `/product` - Product CRUD and listing endpoints
- `/categories` - Category CRUD endpoints

## Project Structure

- `controller/` - REST API controllers
- `services/` - Business logic and services
- `repository/` - MongoDB repositories
- `entities/` - Data models
- `dto/` - Data Transfer Objects
- `utils/` - Utility classes (e.g. JWT utility, API response wrapper)
- `config/` - Security configuration

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests.

## License

This project currently does not specify a license.