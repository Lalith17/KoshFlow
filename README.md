# KoshFlow - Cloud-Native Banking Microservices Platform

## Overview
KoshFlow is a modular, production-grade banking application built with Java, Spring Boot, and Docker. It implements core banking features, leverages event-driven architecture with Kafka and RabbitMQ, and provides robust notification and search capabilities. The system is designed for scalability, extensibility, and future AI integration.

## Architecture
- **Microservices:** Each business domain (accounts, users, transactions, notifications, authentication, search) is a separate Spring Boot service.
- **API Gateway:** Central entry point for all APIs, with JWT-based authentication and routing.
- **Service Discovery:** Eureka registry for dynamic service registration and discovery.
- **Messaging:** Kafka and RabbitMQ for asynchronous, decoupled communication.
- **Persistence:** PostgreSQL for relational data, Elasticsearch for search/analytics.
- **Containerized:** All services and dependencies are orchestrated via Docker Compose.

### Service Architecture Diagram
```mermaid
graph TB
    %% Client and Gateway Layer
    Client[🖥️ Frontend/Client Application]
    Gateway[🌐 API Gateway<br/>Port: 9080]
    
    %% Authentication
    Auth[🔐 Auth Service<br/>JWT Authentication<br/>Port: 8085]
    
    %% Service Discovery
    Eureka[📋 Eureka Server<br/>Service Registry<br/>Port: 8761]
    
    %% Core Services
    User[👤 User Service<br/>Profile Management<br/>Port: 8080]
    Account[💰 Account Service<br/>Balance Management<br/>Port: 8081]
    Trans[💳 Transaction Service<br/>Money Transfers<br/>Port: 8082]
    
    %% Support Services
    Notif[📧 Notification Service<br/>Email & Alerts<br/>Port: 8083]
    ES[🔍 Elasticsearch Service<br/>Search & Analytics<br/>Port: 8084]
    
    %% Message Brokers
    Kafka[(📨 Apache Kafka<br/>Event Streaming)]
    RabbitMQ[(🐰 RabbitMQ<br/>Message Queue)]
    
    %% Client Flow
    Client -->|HTTPS Requests| Gateway
    Gateway -->|Validate Token| Auth
    Gateway -->|Route Requests| User
    Gateway -->|Route Requests| Trans
    Gateway -->|Route Requests| Account
    
    %% Service Discovery (Registration)
    Auth -.->|Register & Discover| Eureka
    User -.->|Register & Discover| Eureka
    Trans -.->|Register & Discover| Eureka
    Account -.->|Register & Discover| Eureka
    Notif -.->|Register & Discover| Eureka
    ES -.->|Register & Discover| Eureka
    Gateway -.->|Register & Discover| Eureka
    
    %% Inter-Service Communication via Kafka
    Trans -->|Transaction Events| Kafka
    Kafka -->|Account Updates| Account
    Account -->|Balance Events| Kafka
    Kafka -->|Transaction Status| Trans
    Kafka -->|User Events| User
    Kafka -->|Search Index| ES
    
    %% RabbitMQ for Notifications
    User -->|Notification Messages| RabbitMQ
    RabbitMQ -->|Send Notifications| Notif
    
    %% Styling
    classDef clientStyle fill:#e1f5fe,stroke:#01579b,stroke-width:2px,color:#000
    classDef gatewayStyle fill:#f3e5f5,stroke:#4a148c,stroke-width:2px,color:#000
    classDef serviceStyle fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px,color:#000
    classDef authStyle fill:#fff3e0,stroke:#e65100,stroke-width:2px,color:#000
    classDef discoveryStyle fill:#fce4ec,stroke:#880e4f,stroke-width:2px,color:#000
    classDef messageStyle fill:#f1f8e9,stroke:#33691e,stroke-width:2px,color:#000
    
    class Client clientStyle
    class Gateway gatewayStyle
    class User,Account,Trans,Notif,ES serviceStyle
    class Auth authStyle
    class Eureka discoveryStyle
    class Kafka,RabbitMQ messageStyle
```

## Services
| Service                | Description                                 | Port | Link to README |
|------------------------|---------------------------------------------|------|----------------|
| user-service           | Manages user profiles                       | 8080 | [user-service](user-service/README.md) |
| account-service        | Manages user accounts and balances          | 8081 | [account-service](account-service/README.md) |
| transaction-service    | Handles money transfers and history         | 8082 | [transaction-service](transaction-service/README.md) |
| notification-service   | Sends notifications (email, etc.)           | 8083 | [notification-service](notification-service/README.md) |
| elasticsearch-service  | Search/analytics for transactions           | 8084 | [elasticsearch-service](elasticsearch-service/README.md) |
| auth-service           | Handles authentication (JWT)                | 8085 | [auth-service](auth-service/README.md) |
| registry-service       | Eureka service registry                     | 8761 | [registry-service](registry-service/README.md) |
| gateway-service        | API Gateway, routing, JWT validation        | 9080 | [gateway-service](gateway-service/README.md) |

## Sample Business Flow: Transaction Processing
1. **Initiate Transaction:**
   - A user initiates a transaction via the `transaction-service` API.
   - The transaction is validated, saved to the database, and published to Kafka.

2. **Account Processing:**
   - The `account-service` consumes the Kafka event, processes the transaction (e.g., balance updates), and publishes the result back to Kafka.

3. **Transaction Completion:**
   - The `transaction-service` listens for the completion event, updates the transaction status, and publishes it to Elasticsearch for indexing.

4. **User Notification:**
   - The `user-service` listens for the completion event, gathers user details, and sends a message to RabbitMQ.
   - The `notification-service` consumes the RabbitMQ message and sends an email to the user.

## Quick Start (Docker Compose)
1. Clone the repository.
2. Ensure Docker and Docker Compose are installed.
3. Run: `docker-compose up --build`
4. Access services via their respective ports (see table above).

## Prerequisites
- Java 21+
- Maven 3.8+
- Docker & Docker Compose

## Configuration
- Each service has its own `application.properties` for environment-specific settings.
- Common environment variables:
  - Database: `spring.datasource.*`
  - Kafka: `spring.kafka.bootstrap-servers`
  - RabbitMQ: `spring.rabbitmq.*`
  - Eureka: `eureka.client.service-url.defaultZone`
  - JWT Secret (auth-service): `jwt.secret`

## Monitoring & Health
- Spring Boot Actuator endpoints are enabled for all services.
- Eureka dashboard available at `http://localhost:8761`.

## Contributing
1. Fork the repo and create a feature branch.
2. Make changes and add tests.
3. Submit a pull request.

## License
MIT

## Contact
For questions or support, contact: mslalith17@gmail.com
