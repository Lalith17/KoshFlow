# Gateway Service

## Overview
The Gateway Service acts as the API gateway for the KoshFlow platform. It routes requests to backend services, applies JWT-based authentication, and integrates with Eureka for service discovery.

## Features
- Central entry point for all APIs
- JWT authentication and validation
- Dynamic routing to microservices
- Service discovery via Eureka

## Package Structure & Responsibilities
- **filters/**: Custom filters for JWT validation and request processing.
- **config/**: Configuration for WebClient and other gateway settings.

## API Routing
The gateway routes requests to the following services:
- /users/** → user-service
- /accounts/** → account-service
- /transactions/** → transaction-service
- /notifications/** → notification-service
- /elasticsearch/** → elasticsearch-service
- /auth/** → auth-service
- /eureka/** → registry-service (Eureka dashboard)

## How Routing and Authentication Work

### 1. Routing
- The gateway uses Spring Cloud Gateway to dynamically route requests to backend services based on the URI path.
- Routes are configured in `application.properties` or programmatically.

### 2. JWT Authentication
- Incoming requests are intercepted by a custom JWT validation filter.
- The filter extracts the token from the `Authorization` header and validates it using the secret key.
- If the token is invalid, the request is rejected with an `UNAUTHORIZED` status.

## Configuration
- **Eureka:** `eureka.client.service-url.defaultZone`
- **JWT Secret:** Used for token validation (see auth-service)

## Build & Run
```sh
mvn clean package
java -jar target/gateway-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `eureka.client.service-url.defaultZone` (Eureka registry)
- `jwt.secret` (JWT signing key, if needed)

## Testing
```sh
mvn test
```

## Troubleshooting
- Ensure all backend services and Eureka are running
- Check Eureka dashboard at http://localhost:8761

## License
MIT
