# Auth Service

## Overview
The Auth Service handles user authentication and JWT token management. It provides endpoints for user login and token validation, integrates with PostgreSQL for user data, and registers with Eureka for service discovery.

## Features
- User authentication with JWT
- Token validation endpoint
- Secure password storage
- Service discovery via Eureka

## Package Structure & Responsibilities
- **controller/**: Handles HTTP API requests for login and token validation.
- **service/**: Business logic for user authentication and JWT token management.
- **dto/**: Data Transfer Objects for API payloads (e.g., LoginRequest).

## API Endpoints
| Method | Endpoint         | Description                  |
|--------|------------------|------------------------------|
| POST   | /auth/login      | Authenticate and get JWT     |
| GET    | /auth/validate   | Validate JWT token           |

## How Authentication Works

### 1. Login (`POST /auth/login`)
- Accepts a `LoginRequest` containing user credentials (username and password).
- Authenticates the user against the database.
- If successful, generates a JWT token and returns it.
- If authentication fails, returns an `UNAUTHORIZED` status.

### 2. Token Validation (`GET /auth/validate`)
- Accepts a JWT token in the `Authorization` header (format: `Bearer <token>`).
- Validates the token using the secret key.
- Returns `OK` if the token is valid, or `UNAUTHORIZED` if invalid.

## Configuration
- **Database:** PostgreSQL (see `application.properties`)
- **JWT Secret:** `jwt.secret`
- **Eureka:** `eureka.client.service-url.defaultZone`

## Build & Run
```sh
mvn clean package
java -jar target/auth-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `spring.datasource.url` (PostgreSQL connection)
- `jwt.secret` (JWT signing key)
- `eureka.client.service-url.defaultZone` (Eureka registry)

## Testing
```sh
mvn test
```

## Troubleshooting
- Ensure PostgreSQL is running and accessible
- Check Eureka registry at http://localhost:8761

## License
MIT
