# Elasticsearch Service

## Overview
The Elasticsearch Service provides search and analytics for transactions. It exposes RESTful APIs for querying transactions by user, status, type, and amount range. It integrates with Elasticsearch for storage and Kafka for event-driven updates. The service registers with Eureka for discovery.

## Features
- Search transactions by user, status, type, and amount
- Retrieve recent and all transactions
- Kafka integration for transaction updates
- Service discovery via Eureka

## Package Structure & Responsibilities
- **controller/**: Handles HTTP API requests for transaction queries.
- **service/**: Business logic for querying transactions in Elasticsearch.
- **enums/**: Domain enums (TransactionStatus, TransactionType).

## API Endpoints
| Method | Endpoint                        | Description                              |
|--------|----------------------------------|------------------------------------------|
| GET    | /elasticsearch/user/{userId}    | Get all transactions for a user          |
| GET    | /elasticsearch/all              | Get all transactions                     |
| GET    | /elasticsearch/recent/{userId}  | Get recent transactions for a user       |
| GET    | /elasticsearch/status?status=   | Get transactions by status               |
| GET    | /elasticsearch/type?type=       | Get transactions by type                 |
| GET    | /elasticsearch/amount-range?min=&max= | Get transactions by amount range   |

## How Search Works

### 1. Querying Transactions
- The service provides multiple endpoints to query transactions based on user ID, status, type, and amount range.
- Each query is processed by the `TransactionService`, which interacts with Elasticsearch to fetch the results.

### 2. Kafka Integration
- Listens to Kafka topics for transaction updates (e.g., `elasticsearch.initiated`).
- Updates the Elasticsearch index with the latest transaction data.

## Configuration
- **Elasticsearch:** `spring.data.elasticsearch.cluster-node`
- **Kafka:** `spring.kafka.bootstrap-servers`
- **Eureka:** `eureka.client.service-url.defaultZone`

## Build & Run
```sh
mvn clean package
java -jar target/elasticsearch-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `spring.data.elasticsearch.cluster-node` (Elasticsearch connection)
- `spring.kafka.bootstrap-servers` (Kafka broker)
- `eureka.client.service-url.defaultZone` (Eureka registry)

## Testing
```sh
mvn test
```

## Troubleshooting
- Ensure Elasticsearch and Kafka are running and accessible
- Check Eureka registry at http://localhost:8761

## License
MIT
