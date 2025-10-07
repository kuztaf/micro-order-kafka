# Order Service

REST service for order management in the food truck system. Allows creating and querying orders, and publishes events to Kafka when an order is created.

## Technologies

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Kafka
- PostgreSQL
- Maven

## Configuration

- Port: 8081
- Database: PostgreSQL on localhost:5432
- Kafka: localhost:9093

## Endpoints

### Create Order
- **POST** `/api/orders`
- Body:
  ```json
  {
    "customerId": "CUST001",
    "items": ["Pizza", "Coca-Cola"]
  }
  ```
- Response: Created Order object

### Get All Orders
- **GET** `/api/orders`
- Response: List of orders

## Kafka Events

When creating an order, publishes an event to "order-events" topic with:
- Key: Order ID (String)
- Value: "ORDER_CREATED"

## How to Run

1. Ensure Docker Compose is running (Kafka and DB).
2. Run:
   ```bash
   mvn spring-boot:run
   ```

## Dependencies

- order-service depends on Kafka and PostgreSQL (via Docker Compose).

## Testing

Use scripts in project root:
- `./create_orders.sh` (5 orders)
- `./create_100_orders.sh` (100 orders)