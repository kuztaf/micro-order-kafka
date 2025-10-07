# Food Truck Microservices

Microservices system for food truck order management using event-driven architecture with Kafka.

## Architecture

- **order-service**: REST service for creating and querying orders. Publishes order creation events to Kafka.
- **notification-service**: Service that consumes Kafka events and generates notifications, storing them in database.

## Technologies

- **Java 21**
- **Spring Boot 3.5.6**
- **Kafka** (with Zookeeper)
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**

## Infrastructure

Infrastructure runs with Docker Compose:
- Kafka + Zookeeper
- PostgreSQL for orders (port 5432)
- PostgreSQL for notifications (port 5433)
- Kafka UI for monitoring (port 8080)

## How to Run

1. **Start infrastructure**:
   ```bash
   docker-compose up -d
   ```

2. **Run order-service**:
   ```bash
   cd order-service
   mvn spring-boot:run
   ```

3. **Run notification-service** (in another terminal):
   ```bash
   cd notification-service
   mvn spring-boot:run
   ```

4. **Verify**:
   - Kafka UI: http://localhost:8080
   - Orders API: http://localhost:8081/api/orders
   - Notifications: Direct DB query on port 5433

## Test Scripts

- `create_orders.sh`: Creates 5 sample orders.
- `create_100_orders.sh`: Creates 100 sample orders.

Run: `./create_orders.sh` or `./create_100_orders.sh`

## Event Flow

1. Client creates order via POST to order-service.
2. order-service saves to DB and publishes "ORDER_CREATED" event to Kafka.
3. notification-service consumes the event, creates notification and saves to DB.

## Monitoring

- Service logs to see Kafka consumption.
- Kafka UI to view messages and consumers.
- DB queries to verify data.