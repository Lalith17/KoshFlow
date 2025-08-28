# User Service

## Overview
The User Service manages user profiles and related operations. It provides RESTful APIs for creating, updating, retrieving, and deleting users. It integrates with PostgreSQL for persistence, Kafka and RabbitMQ for event-driven communication, and registers with Eureka for service discovery.

## Features
- Create, update, retrieve, and delete users
- Kafka and RabbitMQ integration for user-related events
- Service discovery via Eureka

## Package Structure & Responsibilities
- **controller/**: Handles HTTP API requests for user management.
- **service/**: Business logic for user operations and messaging.
- **repo/**: Data access for user profiles.
- **dto/**: Data Transfer Objects for messaging and API payloads.

## API Endpoints
| Method | Endpoint         | Description                  |
|--------|------------------|------------------------------|
| POST   | /api/users/      | Create a new user            |
| PUT    | /api/users/{id}  | Update user by ID            |
| GET    | /api/users/{id}  | Get user by ID               |
| GET    | /api/users/      | Get all users                |
| DELETE | /api/users/{id}  | Delete user by ID            |

## Messaging
- **Kafka:** Listens for transaction events (see code for details).
- **RabbitMQ:** Configured for user notifications (see code for details).

## Configuration
- **Database:** PostgreSQL (see `application.properties`)
- **Kafka:** `spring.kafka.bootstrap-servers`
- **RabbitMQ:** `spring.rabbitmq.*`
- **Eureka:** `eureka.client.service-url.defaultZone`

## Build & Run
```sh
mvn clean package
java -jar target/user-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `spring.datasource.url` (PostgreSQL connection)
- `spring.kafka.bootstrap-servers` (Kafka broker)
- `spring.rabbitmq.host`, `spring.rabbitmq.port`, `spring.rabbitmq.username`, `spring.rabbitmq.password`
- `eureka.client.service-url.defaultZone` (Eureka registry)

## Testing
```sh
mvn test
```

## Troubleshooting
- Ensure PostgreSQL, Kafka, and RabbitMQ are running and accessible
- Check Eureka registry at http://localhost:8761

## License
MIT
