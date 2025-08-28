# Registry Service

## Overview
The Registry Service acts as the Eureka service registry for the KoshFlow platform. It enables dynamic service discovery, allowing microservices to find and communicate with each other without hardcoded endpoints.

## Features
- Eureka server for service registration and discovery
- Central dashboard for monitoring registered services

## API Endpoints
- **Eureka Dashboard:** Accessible at `/` (default: http://localhost:8761)

## Configuration
- **Eureka:** Configured as a server (see `application.properties`)

## Build & Run
```sh
mvn clean package
java -jar target/registry-service-*.jar
```
Or use Docker Compose:
```sh
docker-compose up --build
```

## Environment Variables
- `server.port` (default: 8761)

## Testing
- Access the Eureka dashboard at http://localhost:8761 to verify service registration.

## Troubleshooting
- Ensure the registry-service is running before starting other services.
- Check logs for registration errors if services do not appear in the dashboard.

## License
MIT
