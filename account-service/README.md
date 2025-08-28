# Account Service

## Overview
The Account Service manages user accounts and processes transactions. It provides RESTful APIs for account creation and retrieval, and integrates with Kafka for event-driven transaction processing. The service registers with Eureka for discovery.

## Features
- Create and retrieve accounts
- Process transactions (transfer, withdrawal, deposit)
- Kafka integration for transaction events
- Service discovery via Eureka

## Package Structure & Responsibilities
- **controller/**: Handles HTTP API requests for account creation and retrieval.
- **service/**: Contains business logic for transaction processing and account operations.
- **repo/**: Data access for accounts, including locking for updates.
- **dto/**: Data Transfer Objects for Kafka messaging (TransactionInitiated, TransactionCompletion).
- **enums/**: Domain enums (TransactionType).

## API Endpoints
| Method | Endpoint                | Description                       |
|--------|-------------------------|-----------------------------------|
| POST   | /accounts/              | Create a new account              |
| GET    | /accounts/{accountNumber}| Get account by account number     |
| GET    | /accounts/              | Get all accounts                  |
| GET    | /accounts/user/{userId} | Get accounts for a user           |

## How Transactions Are Processed (Code-Accurate)

### 1. Transaction Initiation
- Listens to the `transaction.initiated` Kafka topic for incoming transaction requests.
- Determines the transaction type (TRANSFER, WITHDRAWAL, DEPOSIT).

### 2. Transaction Types
- **TRANSFER:**
  - Locks both sender and receiver accounts.
  - Validates sufficient balance and user authorization.
  - Updates balances and saves both accounts.
  - Publishes a `TransactionCompletion` event to the `transaction.completed` Kafka topic.
- **WITHDRAWAL:**
  - Locks the sender account.
  - Validates sufficient balance and user authorization.
  - Updates the balance and saves the account.
  - Publishes a `TransactionCompletion` event.
- **DEPOSIT:**
  - Locks the receiver account.
  - Updates the balance and saves the account.
  - Publishes a `TransactionCompletion` event.

### 3. Error Handling
- If any validation or operation fails, publishes a `TransactionCompletion` event with `success=false` and an error reason.

## Messaging
- **Kafka Topics:**
  - `transaction.initiated` (consumed)
  - `transaction.completed` (produced)

## Configuration
- **Database:** PostgreSQL (see `application.properties`)
- **Kafka:** `spring.kafka.bootstrap-servers`
- **Eureka:** `eureka.client.service-url.defaultZone`

## Build & Run
```sh
mvn clean package
java -jar target/account-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `spring.datasource.url` (PostgreSQL connection)
- `spring.kafka.bootstrap-servers` (Kafka broker)
- `eureka.client.service-url.defaultZone` (Eureka registry)

## Testing
```sh
mvn test
```

## Troubleshooting
- Ensure PostgreSQL and Kafka are running and accessible
- Check Eureka registry at http://localhost:8761

## License
MIT
