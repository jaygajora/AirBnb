# Airbnb Backend – Hotel Management System

A production-ready, enterprise-grade backend system engineered from scratch in Java using Spring Boot to replicate the end-to-end workflows of an Airbnb-like hotel booking and property management platform. The application is architected with a strict layered structure (Controller-Service-Repository) and implements advanced features such as strategy-based dynamic pricing, complex booking lifecycles, global cross-cutting exception management, and cookie-based JWT authentication.

---

## 🛠️ Tech Stack & Architecture

* **Framework:** Spring Boot (Java 11+)
* **Build Automation:** Maven (`pom.xml`, `mvnw`)
* **Design Patterns:** Strategy Pattern (for dynamic pricing calculations), Singleton, Layered Architecture, DTO-Entity Mapping
* **Security:** Spring Security, JSON Web Tokens (JWT)
* **Development Environment:** Optimized for IntelliJ IDEA (`.idea/` configuration metadata embedded)

---

## 📁 Detailed Project Layout

```text
├── .idea/                                      # IDE configuration and development metadata[cite: 1]
└── AirBnb/                                     # Root package directory[cite: 1]
    ├── mvnw / mvnw.cmd                         # Maven wrapper executables[cite: 1]
    ├── pom.xml                                 # Dependency definitions and build configurations[cite: 1]
    └── src/
        ├── main/java/com/jay/AirBnb/
        │   ├── Advice/                         # Global cross-cutting concerns for API responses[cite: 1]
        │   │   ├── ApiError.java[cite: 1]
        │   │   ├── ApiResponse.java[cite: 1]
        │   │   ├── GlobalExceptionHandler.java  # Intercepts exceptions across controllers[cite: 1]
        │   │   └── GlobalResponseHandler.java   # Uniformly wraps responses[cite: 1]
        │   │
        │   ├── Config/
        │   │   └── MapperConfig.java           # ModelMapper/Data mapping beans[cite: 1]
        │   │
        │   ├── Controller/                     # REST Endpoints exposing system capability[cite: 1]
        │   │   ├── AdminController.java         # Superuser controls and metric access[cite: 1]
        │   │   ├── AuthController.java          # Login, Registration, Token endpoints[cite: 1]
        │   │   ├── GuestController.java         # Profile operations for guest users[cite: 1]
        │   │   ├── Health.java                  # System heartbeat/uptime monitor[cite: 1]
        │   │   ├── HotelBrowseController.java   # Public-facing, read-optimized search[cite: 1]
        │   │   ├── HotelController.java         # Operations on hotel entities[cite: 1]
        │   │   ├── InventoryController.java     # Inventory allocation and dates[cite: 1]
        │   │   ├── RoomController.java          # Room categorization and capacity management[cite: 1]
        │   │   └── UserController.java          # User account provisioning[cite: 1]
        │   │
        │   ├── Dto/                            # Data Transfer Objects decoupled from persistent layer[cite: 1]
        │   │   ├── BookingDTO.java / BookingRequest.java[cite: 1]
        │   │   ├── GuestDTO.java / UserDTO.java / ProfileUpdateDTO.java[cite: 1]
        │   │   ├── HotelDTO.java / HotelReportDTO.java[cite: 1]
        │   │   ├── InventoryDTO.java / InventoryUpdateRequestDTO.java[cite: 1]
        │   │   └── LoginDTO.java / LoginResponseDTO.java / SignUpRequestDTO.java / SetAdminDTO.java[cite: 1]
        │   │
        │   ├── Entity/                         # Database Schema definitions[cite: 1]
        │   │   ├── BookingEntity.java[cite: 1]
        │   │   ├── GuestEntity.java[cite: 1]
        │   │   ├── HotelContactInfo.java        # Embedded configuration for hotel reachability[cite: 1]
        │   │   ├── HotelEntity.java[cite: 1]
        │   │   ├── InventoryEntity.java[cite: 1]
        │   │   ├── RoomEntity.java[cite: 1]
        │   │   └── UserEntity.java[cite: 1]
        │   │
        │   ├── Enums/                          # Strict domain typing[cite: 1]
        │   │   ├── BookingStatus.java           # e.g., PENDING, CONFIRMED, CANCELLED[cite: 1]
        │   │   ├── Gender.java[cite: 1]
        │   │   └── Role.java                    # GUEST, HOST, ADMIN[cite: 1]
        │   │
        │   ├── Exceptions/                     # Domain-specific runtime exception models[cite: 1]
        │   │   ├── ResourceNotFoundException.java[cite: 1]
        │   │   └── UnauthorisedException.java[cite: 1]
        │   │
        │   ├── Repository/                     # Data Access Layer extending Spring Data JPA[cite: 1]
        │   │   ├── BookingRepository.java[cite: 1]
        │   │   ├── GuestRepository.java[cite: 1]
        │   │   ├── HotelRepository.java[cite: 1]
        │   │   ├── InventoryRepository.java[cite: 1]
        │   │   ├── RoomRepository.java[cite: 1]
        │   │   └── UserRepository.java[cite: 1]
        │   │
        │   ├── Security/                       # Spring Security configuration and filters[cite: 1]
        │   │   ├── AuthService.java[cite: 1]
        │   │   ├── JWTAuthFilter.java           # Stateful interceptor for request-bound credentials[cite: 1]
        │   │   ├── JWTService.java              # Token creation, signing, and verification[cite: 1]
        │   │   └── WebSecurityConfig.java       # Access-control rules and security chains[cite: 1]
        │   │
        │   ├── Service/                        # Business Logic Implementation[cite: 1]
        │   │   ├── Interface/                   # Decoupled loose-coupling interfaces[cite: 1]
        │   │   │   ├── AdminService.java / BookingService.java / GuestService.java[cite: 1]
        │   │   │   ├── HotelService.java / InventoryService.java / RoomService.java / UserService.java[cite: 1]
        │   │   ├── AdminServiceImplementation.java[cite: 1]
        │   │   ├── BookingServiceImplementation.java[cite: 1]
        │   │   ├── GuestServiceImplementation.java[cite: 1]
        │   │   ├── HotelServiceImplementation.java[cite: 1]
        │   │   ├── InventoryServiceImplementation.java[cite: 1]
        │   │   ├── RoomServiceImplementation.java[cite: 1]
        │   │   └── UserServiceImplementation.java[cite: 1]
        │   │
        │   ├── Strategy/                       # Extensible Dynamic Pricing Modules[cite: 1]
        │   │   ├── PricingStrategy.java         # Master interface[cite: 1]
        │   │   ├── BasePricingStrategy.java     # Default static standard tier[cite: 1]
        │   │   ├── HolidayPricingStrategy.java  # Seasonal modifiers[cite: 1]
        │   │   ├── OccupancyPricingStrategy.java# Real-time room availability metrics[cite: 1]
        │   │   ├── SurgePricingStrategy.java    # Traffic/High demand algorithms[cite: 1]
        │   │   ├── UrgencyPricingStrategy.java  # Last-minute booking modifiers[cite: 1]
        │   │   └── PricingService.java          # Strategy orchestrator[cite: 1]
        │   │
        │   └── AirBnbApplication.java          # Core Application Entry Point[cite: 1]
        │
        └── test/java/com/jay/AirBnb/
            └── AirBnbApplicationTests.java     # Suite for context verification and integration test[cite: 1]
```

## ⚡ Key Engineering Subsystems

### 1. Extensible Pricing Engine (Strategy Pattern)
Rather than hardcoding rates, the platform delegates cost calculations to an engine comprising `PricingService` and concrete strategies (`Base`, `Holiday`, `Occupancy`, `Surge`, `Urgency`)[cite: 1]. This design calculates accommodation costs dynamically based on seasonal trends, proximity to travel dates, and current venue capacity[cite: 1].

### 2. Hardened Token-Based Authentication
* **JWT Filter Layer:** Implements `JWTAuthFilter` along with `WebSecurityConfig` to intercept inbound traffic, parse user scopes, and protect endpoints based on application roles (`GUEST`, `HOST`, `ADMIN`)[cite: 1].
* **Secure Cookies:** Access tokens are delivered via secure HTTP-only cookies rather than standard raw API string bodies to shield the client state from Cross-Site Scripting (XSS) extractions.

### 3. Inventory & Booking Synchronization
Features complete persistence state tracking via `BookingStatus` (handling states such as pending holds or confirmations)[cite: 1]. A specialized `InventoryService` ensures rooms are calculated dynamically across ranges of check-in and check-out selections, preventing double-bookings[cite: 1].

### 4. Consolidated Reporting Analytics
* Provides property managers and administrators aggregated insights through the `HotelReportDTO`[cite: 1].
* Generates occupancy charts and revenue analysis calculations directly from the repository tier using custom database expressions via `getHotelReport()`.

---

## 🚀 Getting Started

### Prerequisites
* **Java Development Kit (JDK):** Version 11 or higher[cite: 1].
* **Database Platform:** Configured profile matching target relational properties (e.g., MySQL, H2, or PostgreSQL).

### Local Deployment Steps

1. **Clone the project:**
   ```bash
   git clone [https://github.com/jaygajora/AirBnb.git](https://github.com/jaygajora/AirBnb.git)
   cd AirBnb/AirBnb
   
### Local Deployment Steps

1. **Configure Database Settings:**
   * Modify the database connection credentials located under `src/main/resources/application.properties` (or `application.yml`) to align with your local server.

2. **Compile and Build:**
   ```bash
   # Using Unix Wrapper
   ./mvnw clean install
   ```
   # Using Windows Command Prompt Wrapper
   ```bash
   mvnw.cmd clean install
   ```
   ## Running the Application Locally

You can run the Spring Boot application locally using the included Maven Wrapper. Execute the following command in your terminal:

```bash
./mvnw spring-boot:run
