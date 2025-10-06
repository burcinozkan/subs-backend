# Subscription Tracker Backend

A backend application built with **Spring Boot** for managing users, authentication, and subscriptions.  
Supports user registration, JWT-based login, and CRUD operations for subscriptions.

---

## Tech Stack
- Java 17  
- Spring Boot 3  
- PostgreSQL  
- JPA / Hibernate  
- Flyway  
- Docker  
- JWT Authentication

---
## API Endpoints

| Method | Endpoint             | Description                   |
| ------ | -------------------- | ----------------------------- |
| POST   | `/api/auth/register` | Register a new user           |
| POST   | `/api/auth/login`    | Login and receive a JWT token |
| GET    | `/api/subscriptions` | List all subscriptions        |
| POST   | `/api/subscriptions` | Add a new subscription        |

## Project Structure
```
src/
 ├─ main/
 │   ├─ java/com/subsapp/
 │   │   ├─ controller/
 │   │   ├─ model/
 │   │   ├─ repository/
 │   │   ├─ service/
 │   │   └─ config/
 │   └─ resources/
 │       ├─ db/migration/
 │       └─ application.example.properties
 └─ test/

