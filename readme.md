# 🛒 E-Com Backend

A Spring Boot + MongoDB backend for an **E-commerce platform** with authentication, role-based access, product management, cart system, orders, and payment flow.

---

## 🚀 Features

### 🔑 Authentication & Authorization
- User registration & login with **JWT**
- Role-based access: **Customer** & **Admin**
- Secure APIs using Spring Security
- Passwords stored securely using **BCrypt**

### 📦 Products & Categories
- Admin: Create, update, delete products & categories
- Customer: Browse products with pagination, search & filtering

### 🛒 Cart System
- Add/remove products to cart
- Auto-calculation of cart total
- Prevents duplicate entries (merges quantities)
- Fetch user-specific cart

### 📑 Orders & Payments
- Place orders directly from cart
- Track order status: `PENDING → CONFIRMED → SHIPPED → DELIVERED`
- Mock payment integration (mark order as paid)
- Ready for integration with real gateways (Razorpay, Stripe, Cashfree)

---

## 🏗️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data MongoDB**
- **Lombok**
- **Validation API**
- **Swagger / OpenAPI (for API Docs)**
- **Maven**

---

## 📂 Project Structure

```
src/main/java/com/yodha/e_com
 ├── config          # Security & JWT filters
 ├── controller      # REST controllers (Auth, Products, Cart, Orders, Payments)
 ├── dto             # Request & Response DTOs
 ├── entities        # MongoDB document models
 ├── exception       # Global exception handling
 ├── mapper          # Entity ↔ DTO mappers
 ├── repository      # Mongo repositories
 ├── security        # JWT & UserDetailsService
 ├── services        # Business logic layer
 └── EComApplication.java  # Main Spring Boot application
```

---

## ⚙️ Setup & Installation

### 1. Clone Repository
```bash
git clone https://github.com/your-username/E-com.git
cd E-com
```

### 2. Configure MongoDB
- Local MongoDB or [MongoDB Atlas](https://www.mongodb.com/atlas)
- Update `application.yml` or `application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/ecom
jwt.secret=your_secret_key
```

### 3. Build & Run
```bash
./mvnw spring-boot:run
```

Server starts at: `http://localhost:8080`

---

## 🔗 API Endpoints

### Auth
- `POST /auth/register` → Register new user
- `POST /auth/login` → Login & get JWT
- `GET /auth/user` → Get current user profile

### Products & Categories
- `POST /categories` (Admin) → Add category
- `POST /products` (Admin) → Add product
- `GET /products` (Customer) → Fetch all products (with pagination & filtering)
- `GET /products/{id}` (Customer) → Fetch product by ID
- `PUT /products/{id}` (Admin) → Update product
- `DELETE /products/{id}` (Admin) → Delete product

### Cart
- `POST /cart` → Add item to cart
- `GET /cart` → Get user’s cart
- `DELETE /cart/{itemId}` → Remove item

### Orders & Payments
- `POST /orders` → Place order
- `GET /orders` → Fetch past orders
- `POST /payments` → Mock payment (updates order status)

---

## 📖 API Documentation
Swagger/OpenAPI is available at:
```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Deliverables Covered (Day-wise)

- **Day 1:** Project setup, MongoDB connection ✔
- **Day 2:** JWT Authentication & User Management ✔
- **Day 3:** Products & Categories CRUD ✔
- **Day 4:** Cart System ✔
- **Day 5:** Orders & Payments ✔

---

## 📈 Extra Features (Future)
- Swagger UI for API documentation
- Environment-based configs (dev/prod)
- Future-ready for payment integration

---

## 👤 Author
**Avaneesh Jaiswal**  
📍 Lucknow, India  
📧 [workwithavaneesh@gmail.com](mailto:workwithavaneesh@gmail.com)  
🔗 [LinkedIn](https://www.linkedin.com/in/avaneeshjaiswal/)

---
