# Notification Service

Service that consumes Kafka events related to orders and generates notifications, storing them in database.

## Technologies

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Kafka
- PostgreSQL
- Maven

## Configuration

- Port: 8082
- Database: PostgreSQL on localhost:5433
- Kafka: localhost:9093

## Functionality

- Consumes messages from "order-events" topic.
- For each "ORDER_CREATED" event, creates a notification with message "Order X has event: ORDER_CREATED".
- Saves notification to DB with status "SENT".
- Logs the sending (in production, would send email/SMS).

## Consumed Events

- Topic: "order-events"
- Group ID: "notification-group"
- Processes "ORDER_CREATED" events

## How to Run

1. Ensure Docker Compose is running (Kafka and DB).
2. Run:
   ```bash
   mvn spring-boot:run
   ```

## Dependencies

- notification-service depends on Kafka and PostgreSQL (via Docker Compose).
- Runs after order-service to consume events.

## Verification

- Check logs for "Notification sent: Order X has event: ORDER_CREATED" messages.
- Query DB on port 5433 to see saved notifications.
- In Kafka UI, verify consumption in "notification-group" group.