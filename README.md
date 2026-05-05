# PG Rental — Spring Boot Backend

## Tech Stack
- Java 17 · Spring Boot 3.2 · Spring Security · JPA/Hibernate · MySQL · JWT · Lombok

---

## Project Structure

```
src/main/java/com/example/Backend/
├── BackendApplication.java
├── controller/
│   ├── AuthController.java          ← /api/auth/register  /api/auth/login
│   ├── PgController.java            ← /api/pgs/**
│   ├── BookingController.java       ← /api/bookings/**
│   └── UserDashboardController.java ← /api/user/dashboard/**
├── service/
│   ├── UserService.java
│   ├── LoginService.java            ← UserDetailsService impl
│   ├── PgService.java
│   ├── BookingService.java
│   └── UserDashboardService.java
├── repository/
│   ├── UserRepository.java
│   ├── PgRepository.java
│   └── BookingRepository.java
├── entity/
│   ├── User.java
│   ├── Pg.java
│   ├── Booking.java
│   ├── Role.java                    ← OWNER | USER
│   ├── BookingStatus.java           ← PENDING | APPROVED | REJECTED
│   └── GenderPreference.java        ← MALE | FEMALE | ANY
├── dto/
│   ├── UserRequestDTO.java
│   ├── UserResponseDTO.java
│   ├── LoginRequestDTO.java
│   ├── LoginResponseDTO.java
│   ├── PgRequestDTO.java
│   ├── PgResponseDTO.java
│   ├── BookingRequestDTO.java
│   ├── BookingResponseDTO.java
│   ├── UserBookingDTO.java
│   ├── DashboardStatsDTO.java
│   └── ErrorResponse.java
├── security/
│   ├── SecurityConfig.java
│   ├── JWTUtil.java
│   └── JWTAuthenticationFilter.java
└── exception/
    ├── CustomException.java
    └── GlobalExceptionHandler.java
```

---

## API Endpoints

### Auth (public)
| Method | URL                  | Description       |
|--------|----------------------|-------------------|
| POST   | /api/auth/register   | Register new user |
| POST   | /api/auth/login      | Login → JWT token |

### PG Listings
| Method | URL              | Role          | Description          |
|--------|------------------|---------------|----------------------|
| POST   | /api/pgs         | OWNER         | Create PG            |
| GET    | /api/pgs         | USER / OWNER  | Get all PGs          |
| GET    | /api/pgs/{id}    | USER / OWNER  | Get PG by ID         |
| GET    | /api/pgs/search  | USER / OWNER  | Search PGs           |
| GET    | /api/pgs/my      | OWNER         | Get owner's own PGs  |
| PUT    | /api/pgs/{id}    | OWNER         | Update own PG        |
| DELETE | /api/pgs/{id}    | OWNER         | Delete own PG        |

### Bookings
| Method | URL                       | Role  | Description            |
|--------|---------------------------|-------|------------------------|
| POST   | /api/bookings             | USER  | Create booking         |
| GET    | /api/bookings/my          | USER  | View own bookings      |
| GET    | /api/bookings/owner       | OWNER | View PG booking requests |
| PUT    | /api/bookings/{id}/status | OWNER | Approve / Reject       |

### User Dashboard
| Method | URL                          | Role | Description              |
|--------|------------------------------|------|--------------------------|
| GET    | /api/user/dashboard/stats    | USER | Counts for bar chart     |
| GET    | /api/user/dashboard/bookings | USER | All bookings table data  |

---

## Setup

1. Create MySQL database: `CREATE DATABASE pg_rental;`
2. Update `application.properties` with your DB credentials
3. Run: `mvn spring-boot:run`
