# Notification Service

## Overview
The Notification Service handles delivery of notifications (such as email) triggered by events in the system. It integrates with RabbitMQ for event-driven messaging and uses SMTP for email delivery. The service registers with Eureka for discovery.

## Features
- Sends notifications via email (Gmail SMTP configured)
- Transaction completion notifications
- Welcome emails for new users
- Service discovery via Eureka

## Package Structure & Responsibilities
- **service/**: Business logic for sending email notifications.
- **dto/**: Data Transfer Objects for notification payloads (e.g., TransactionCompletedNotification, NewUserCreated).
- **rabbitmq/**: Configuration and consumers for RabbitMQ messaging.

## Notification Flows

### 1. Transaction Completion Notifications
- Listens to RabbitMQ for transaction completion events.
- Sends an email to the user with details about the transaction (e.g., ID, type, success/failure, message).

### 2. New User Welcome Emails
- Listens to RabbitMQ for new user registration events.
- Sends a welcome email to the new user, highlighting key features of the platform.

## Configuration
- **Mail:** `spring.mail.*` (SMTP settings)
- **RabbitMQ:** `spring.rabbitmq.*`
- **Eureka:** `eureka.client.service-url.defaultZone`

## Build & Run
```sh
mvn clean package
java -jar target/notification-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `spring.mail.host`, `spring.mail.port`, `spring.mail.username`, `spring.mail.password`
- `spring.rabbitmq.host`, `spring.rabbitmq.port`, `spring.rabbitmq.username`, `spring.rabbitmq.password`
- `eureka.client.service-url.defaultZone` (Eureka registry)

## Testing
```sh
mvn test
```

## Troubleshooting
- Ensure RabbitMQ and SMTP server are running and accessible
- Check Eureka registry at http://localhost:8761

## License
MIT
