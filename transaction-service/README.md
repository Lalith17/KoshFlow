# Transaction Service

## Overview
The Transaction Service manages money transfers and transaction history. It exposes RESTful APIs for initiating transactions and retrieving transaction records. It integrates with Kafka for event-driven messaging and PostgreSQL for persistence. The service registers with Eureka for discovery.

## Features
- Initiate money transfers
- Retrieve all transactions
- Kafka integration for transaction events
- Service discovery via Eureka

## Package Structure & Responsibilities
- **controller/**: Handles HTTP API requests and validation.
- **service/**: Business logic for transaction creation, idempotency, Kafka messaging, and status updates.
- **repo/**: Data access for transactions.
- **dto/**: Data Transfer Objects for API and messaging (TransactionRequest, TransactionInitiated, TransactionCompletion).
- **kafkaListeners/**: Kafka consumers for transaction events.
- **enums/**: Domain enums (TransactionStatus, TransactionType).

## API Endpoints
| Method | Endpoint           | Description                        |
|--------|--------------------|------------------------------------|
| POST   | /transactions/transfer | Initiate a transaction         |
| GET    | /transactions/     | Get all transactions               |

## How a Transaction Flows Through the System (Code-Accurate)

### 1. Initiate Transfer (POST /transactions/transfer)
- Validates the request (idempotency key, fromId/toId, userId, amount > 0).
- Checks for duplicate transaction by idempotency key.
- Creates a new Transaction (type: TRANSFER/WITHDRAWAL/DEPOSIT, status: PENDING) and saves to DB.
- Publishes a TransactionInitiated DTO to Kafka topic `transaction.initiated`.
- If Kafka send fails, marks transaction as FAILED in DB.
- Returns status message ("Transaction Initiated", "Duplicate Transaction", or "Transaction Failed").

### 2. Account Service Processing (Implied)
- Listens to `transaction.initiated` Kafka topic.
- Locks accounts, checks balances, applies business rules.
- Updates balances and sets transaction status to COMPLETED or FAILED.
- Publishes TransactionCompletion event to Kafka (e.g., `transaction.completion`).

### 3. Transaction Service Completion
- Listens for TransactionCompletion events from Kafka.
- Updates the transaction record in DB to COMPLETED or FAILED.
- Sends updated transaction to `elasticsearch.initiated` Kafka topic for indexing.

### 4. User Notification (Implied)
- User-service listens for completed transactions on Kafka.
- Gathers user details and sends a message to RabbitMQ.
- Notification-service listens to RabbitMQ and sends an email to the user.

## Messaging
- **Kafka Topics:**
  - `transaction.initiated` (produced)
  - `transaction.completion` (consumed)
  - `elasticsearch.initiated` (produced)

## Configuration
- **Database:** PostgreSQL (see `application.properties`)
- **Kafka:** `spring.kafka.bootstrap-servers`
- **Eureka:** `eureka.client.service-url.defaultZone`

## Build & Run
```sh
mvn clean package
java -jar target/transaction-service-*.jar
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
