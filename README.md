# Subscription Tracker Backend

A **Spring Boot 3** backend service for managing user authentication and subscription tracking.  
It powers the **Subscription Tracker mobile application**, handling secure login, data persistence, and automatic subscription management.

---

##  Tech Stack

| Layer | Technology |
|-------|-------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3 |
| **Database** | PostgreSQL |
| **ORM** | JPA / Hibernate |
| **Migration** | Flyway |
| **Security** | Spring Security + JWT |
| **Containerization** | Docker & Docker Compose |

---
##  Project Structure
```
src/
├─ main/
│ ├─ java/com/subsapp/
│ │ ├─ controller/ # REST Controllers (Auth, Subscription)
│ │ ├─ model/ # Entities (User, Subscription)
│ │ ├─ repository/ # JPA Repositories
│ │ ├─ service/ # Business Logic Layer
│ │ └─ config/ # Security, JWT, and App Config
│ └─ resources/
│ ├─ db/migration/ # Flyway SQL Migrations
│ └─ application.properties
└─ test/ # Unit & Integration Tests
```
---

## API Endpoints

| Method     | Endpoint                  | Description                    |
| ---------- | ------------------------- | ------------------------------ |
| **POST**   | `/api/auth/register`      | Register a new user            |
| **POST**   | `/api/auth/login`         | Authenticate user & return JWT |
| **GET**    | `/api/subscriptions`      | Get all user subscriptions     |
| **POST**   | `/api/subscriptions`      | Add new subscription           |
| **PUT**    | `/api/subscriptions/{id}` | Update subscription            |
| **DELETE** | `/api/subscriptions/{id}` | Delete subscription            |


## Database Migrations (Flyway)
```
Flyway automatically runs migration scripts in
src/main/resources/db/migration
Each file is versioned like:

V1__init_schema.sql
V2__add_subscriptions_table.sql
V3__add_indexes.sql
```

