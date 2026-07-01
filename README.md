# 🏨 AirBnB Backend API

<div align="center">

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green?style=for-the-badge&logo=springsecurity)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-success?style=for-the-badge)

### A scalable Spring Boot backend for an Airbnb-style hotel booking platform featuring JWT authentication, hotel management, room inventory, dynamic pricing, and booking services.

</div>

---

# 📖 Overview

This project is a backend REST API for an Airbnb-like hotel booking platform.

The application enables users to browse hotels, manage guests, make bookings, and allows hotel administrators to manage hotels, rooms, and inventory securely using JWT authentication.

The backend follows a layered architecture using Spring Boot best practices.

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

### 🔐 Authentication
- User Registration
- User Login
- JWT Authentication
- Refresh Token Support
- Role-Based Authorization

### 👤 User Management
- User Profile
- Profile Update
- Admin Promotion

### 🏨 Hotel Management
- Create Hotel
- Update Hotel
- Delete Hotel
- Enable/Disable Hotel
- View Hotel Information

### 🛏 Room Management
- Add Rooms
- Update Rooms
- Delete Rooms
- View Rooms

### 📦 Inventory Management
- Manage Room Availability
- Dynamic Surge Pricing
- Booking Count Tracking

### 👥 Guest Management
- Add Guests
- Update Guests
- Delete Guests
- View Guests

### 💰 Dynamic Pricing
- Strategy Pattern Implementation
- Surge Pricing Support
- Inventory-based Pricing

### 🛡 Security
- Spring Security
- JWT Authentication
- Access & Refresh Tokens
- Role-Based Access Control

---

# 🏗 Architecture

```
                Client
                   │
             REST Controllers
                   │
              Service Layer
                   │
          Repository (JPA)
                   │
             PostgreSQL DB
```

The project follows a clean layered architecture:

```
Controller
      │
Service
      │
Repository
      │
Database
```

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|----------|
| Java 25 | Programming Language |
| Spring Boot 3.5 | Backend Framework |
| Spring Security | Authentication |
| JWT | Authorization |
| Spring Data JPA | ORM |
| PostgreSQL | Database |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |
| ModelMapper | DTO Mapping |

---

# 📂 Project Structure

```
src
│
├── main
│   ├── java
│   │
│   └── com.jay.AirBnb
│       ├── Advice
│       ├── Config
│       ├── Controller
│       ├── DTO
│       ├── Entity
│       ├── Enums
│       ├── Exception
│       ├── Repository
│       ├── Security
│       ├── Service
│       ├── Strategy
│       └── AirBnbApplication.java
│
└── test
```

---

# 🗄 Database Schema

<p align="center">
<img src="AirBnb/docs/database.schema.jpeg" alt="Database Schema" width="1000">
</p>

### Main Entities

- User
- Guest
- Hotel
- Room
- Inventory
- Booking
- BookingGuest
- Payment
- ContactInfo

---

# 🔐 Authentication Flow

```
User Login
      │
      ▼
Authenticate Credentials
      │
      ▼
Generate Access Token
      │
      ▼
Generate Refresh Token
      │
      ▼
Client Stores Tokens
      │
      ▼
Authorization Header
      │
      ▼
JWT Filter Validation
      │
      ▼
Protected APIs
```

---

# 📡 REST API Reference

---

## 🔐 Authentication APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/signup` | Register a new user |
| POST | `/auth/login` | Login and receive JWT tokens |
| POST | `/auth/refresh` | Generate a new Access Token using Refresh Token |

---

## 👤 User APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/profile` | Retrieve logged-in user's profile |
| PATCH | `/user/profile` | Update logged-in user's profile |
| POST | `/user/set-user-as-admin` | Promote a user to Admin |

---

## 👥 Guest APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/user/guests` | Add a new guest |
| GET | `/user/guests` | Retrieve all guests |
| GET | `/user/guests/{guestId}` | Retrieve guest by ID |
| PATCH | `/user/guests/{guestId}` | Update guest details |
| DELETE | `/user/guests/{guestId}` | Delete guest |

---

## 🏨 Hotel APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/hotel` | Create a new hotel |
| GET | `/admin/hotel` | Retrieve all hotels owned by the admin |
| GET | `/admin/hotel/{id}` | Retrieve hotel details |
| PATCH | `/admin/hotel/{id}` | Update hotel details |
| DELETE | `/admin/hotel/{id}` | Delete hotel |
| PATCH | `/admin/hotel/{id}/toggle-active` | Enable or disable hotel |

---

## 🌍 Hotel Browse APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/hotel/{id}/info` | Retrieve public hotel information |

---

## 🛏 Room APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/hotel/{hotelId}/rooms` | Add a room to a hotel |
| GET | `/admin/hotel/{hotelId}/rooms` | Retrieve all rooms |
| GET | `/admin/hotel/{hotelId}/rooms/{roomId}` | Retrieve room details |
| PATCH | `/admin/hotel/{hotelId}/rooms/{roomId}` | Update room |
| DELETE | `/admin/hotel/{hotelId}/rooms/{roomId}` | Delete room |

---

## 📦 Inventory APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/inventory/room/{roomId}` | Retrieve room inventory |
| PATCH | `/admin/inventory/room/{roomId}` | Update room inventory and surge pricing |

---

## 👑 Admin APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/set-user-as-admin` | Grant Admin privileges to a user |

---

# 🧩 Design Patterns Used

### ✅ Strategy Pattern

Used for implementing dynamic hotel pricing strategies.

Benefits:

- Easily add new pricing rules
- No modification of existing code
- Open/Closed Principle

---

### ✅ Repository Pattern

Used for database abstraction.

---

### ✅ DTO Pattern

Separates Entity models from API responses.

---

### ✅ Global Exception Handling

Centralized exception handling using Spring's `@ControllerAdvice`.

---

# 🚀 Getting Started

## Prerequisites

- Java 25+
- Maven
- PostgreSQL

---

## Clone Repository

```bash
git clone https://github.com/yourusername/airbnb-backend.git

cd airbnb-backend
```

---

## Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/airbnb

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

Application runs on

```
http://localhost:8080
```

---

# 🧪 Testing

Run tests

```bash
mvn test
```

---

# 📊 Project Highlights

- ✅ Spring Boot 3.5
- ✅ Java 25
- ✅ PostgreSQL
- ✅ JWT Authentication
- ✅ Refresh Tokens
- ✅ Spring Security
- ✅ Role-Based Access Control
- ✅ Dynamic Pricing Strategy
- ✅ Inventory Management
- ✅ Hotel Management
- ✅ Room Management
- ✅ Guest Management
- ✅ DTO Architecture
- ✅ Repository Pattern
- ✅ Strategy Pattern
- ✅ Global Exception Handling

---

# 🚀 Future Improvements

- Payment Gateway Integration
- Hotel Reviews
- Ratings
- Wishlist
- Redis Caching
- Docker Support
- Kubernetes Deployment
- Swagger/OpenAPI Documentation
- Email Notifications
- CI/CD Pipeline
- Image Upload Service
- Elasticsearch for Hotel Search

---

# 👨‍💻 Author

**Jay Dinesh Gajora**

Backend Developer | Java | Spring Boot | PostgreSQL | REST APIs | Spring Security

LinkedIn: https://linkedin.com/in/jaygajora

---

# 📄 License

This project is licensed under the MIT License.

---

<div align="center">

⭐ If you found this project useful, consider giving it a star!

Built with ❤️ using Spring Boot, Java & PostgreSQL

</div>
