# 🏠 AirBnb Backend API

<div align="center">

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-green?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-success?style=for-the-badge)

**A scalable Spring Boot backend for an Airbnb-style hotel booking platform with secure authentication, hotel management, dynamic pricing, inventory management, and booking services.**

</div>

---

# 📖 Overview

AirBnb Backend is a RESTful backend application built using **Spring Boot** that powers an Airbnb-like hotel booking platform.

The project provides APIs for:

- 🔐 Secure Authentication using JWT
- 🏨 Hotel Management
- 👤 Guest Management
- 📅 Booking Management
- 🛏 Room & Inventory Management
- 💰 Dynamic Pricing Strategies
- 📊 Admin Reporting
- 🛡 Role-Based Authorization

The architecture follows a clean layered design making the application scalable, maintainable, and easy to extend.

---

# ✨ Features

## Authentication

- User Registration
- Login
- JWT Authentication
- Spring Security
- Role-based Authorization

---

## Hotel Management

- Add Hotels
- Update Hotel Details
- View Hotels
- Browse Hotels
- Hotel Contact Information

---

## Guest Management

- Guest Registration
- Guest Details
- Guest Bookings
- Guest Profile

---

## Booking Management

- Create Booking
- Cancel Booking
- Booking Status Tracking
- Booking History

---

## Inventory Management

- Room Availability
- Room Inventory
- Occupancy Tracking

---

## Dynamic Pricing

Pricing is implemented using the **Strategy Design Pattern**.

Available strategies include:

- Base Pricing
- Holiday Pricing
- Occupancy Pricing

This makes pricing easily extendable without modifying existing code.

---

## Admin Features

- Hotel Reports
- Booking Reports
- Guest Reports
- Administrative APIs

---

# 🏗 Architecture

```
Client
   │
REST API
   │
Controllers
   │
Services
   │
Repositories
   │
PostgreSQL Database
```

The project follows a layered architecture:

```
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|----------|
| Java 25 | Programming Language |
| Spring Boot 3.5 | Backend Framework |
| Spring Security | Authentication & Authorization |
| JWT | Secure Authentication |
| Spring Data JPA | ORM |
| PostgreSQL | Database |
| Maven | Dependency Management |
| Lombok | Boilerplate Reduction |
| ModelMapper | DTO Mapping |

---

# 📂 Project Structure

```
src
 ├── main
 │   ├── java
 │   │    └── com.jay.AirBnb
 │   │
 │   ├── Advice
 │   ├── Config
 │   ├── Controller
 │   ├── DTO
 │   ├── Entity
 │   ├── Enums
 │   ├── Exceptions
 │   ├── Repository
 │   ├── Security
 │   ├── Service
 │   │      └── Interface
 │   ├── Strategy
 │   └── AirBnbApplication.java
 │
 └── test
```

---

# 🔐 Authentication Flow

```
User Login
      │
      ▼
Authenticate Credentials
      │
      ▼
Generate JWT Token
      │
      ▼
Client stores Token
      │
      ▼
Token sent in Authorization Header
      │
      ▼
JWT Filter validates request
      │
      ▼
Access Protected APIs
```

---

# 🧩 Design Patterns Used

### ✅ Strategy Pattern

Used for dynamic hotel pricing.

Current strategies:

- Base Pricing
- Holiday Pricing
- Occupancy Pricing

---

### DTO Pattern

Used to separate Entity models from API responses.

---

### Repository Pattern

Used for clean database abstraction.

---

### Global Exception Handling

Centralized exception management using:

- GlobalExceptionHandler
- ApiError
- ApiResponse

---

# 🚀 Getting Started

## Prerequisites

- Java 25+
- Maven
- PostgreSQL
- IntelliJ IDEA (Recommended)

---

# ⚙ Installation

Clone the repository

```bash
git clone https://github.com/yourusername/airbnb-backend.git
```

Move inside project

```bash
cd AirBnb
```

Install dependencies

```bash
mvn clean install
```

---

# 🗄 Configure Database

Create a PostgreSQL database.

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/airbnb

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

---

# ▶ Running the Application

Using Maven

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

Application starts at

```
http://localhost:8080
```

---

# 📦 API Modules

### Authentication

- Register
- Login
- JWT Token Generation

---

### Hotels

- Create Hotel
- Update Hotel
- Browse Hotels
- View Hotels

---

### Guests

- Register Guest
- View Guest
- Guest Details

---

### Booking

- Book Hotel
- Cancel Booking
- Booking Status

---

### Inventory

- Add Inventory
- Update Inventory
- Room Availability

---

### Admin

- Reports
- Hotel Management
- Booking Management

---

# 🛡 Security

The project uses:

- Spring Security
- JWT Authentication
- Authentication Filter
- Authorization
- Protected REST APIs

---

# 📊 Database

Main entities include:

- Guest
- Hotel
- Booking
- Room
- Inventory
- Hotel Contact

Relationships are managed using Spring Data JPA.

---

# 📈 Future Improvements

- Payment Gateway Integration
- Email Notifications
- Hotel Reviews
- Ratings
- Image Upload
- Search Filters
- Wishlist
- Redis Caching
- Docker Support
- Kubernetes Deployment
- Swagger/OpenAPI Documentation
- CI/CD Pipeline

---

# 🧪 Testing

Run all tests

```bash
mvn test
```

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/new-feature
```

3. Commit changes

```bash
git commit -m "Add new feature"
```

4. Push

```bash
git push origin feature/new-feature
```

5. Create a Pull Request

---

# 👨‍💻 Author

**Jay Dinesh Gajora**

- Java Backend Developer
- Spring Boot Developer
- PostgreSQL
- REST APIs
- Spring Security
- JWT Authentication

GitHub: **https://github.com/yourusername**

LinkedIn: **https://linkedin.com/in/yourprofile**

---

# 📄 License

This project is licensed under the MIT License.

---

# ⭐ If you found this project helpful

Please consider giving it a ⭐ on GitHub!

---

<div align="center">

**Built with ❤️ using Spring Boot & Java**

</div>
