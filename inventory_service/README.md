# Inventory Service

The Inventory Service manages product stock levels and handles inventory operations for the Food Truck system. It integrates with the Order Service via Kafka to automatically reserve and release inventory when orders are placed or cancelled.

## Features

- Product management with SKU tracking
- Stock level monitoring and low stock alerts
- Automatic inventory reservation on order placement
- Inventory release on order cancellation
- Stock movement audit trail
- REST API for inventory operations
- Kafka integration for event-driven architecture

## Technologies

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Lombok
- Maven

## API Endpoints

### Products

- `POST /api/inventory` - Create a new product
- `PUT /api/inventory/{sku}` - Update product information
- `GET /api/inventory` - Get all products
- `GET /api/inventory/{sku}` - Get product by SKU
- `GET /api/inventory/check/{sku}?quantity=X` - Check if sufficient stock available
- `GET /api/inventory/low-stock?threshold=X` - Get products with low stock

## Events

### Consumed Events (from order-events topic)
- `OrderPlacedEvent` - Reserves inventory for order items
- `OrderCancelledEvent` - Releases reserved inventory

### Published Events (to inventory-events topic)
- `InventoryReservedEvent` - Published when inventory is successfully reserved
- `InventoryReleasedEvent` - Published when inventory is released

## Database Schema

### Product Table
- `id` (UUID, Primary Key)
- `sku` (String, Unique)
- `name` (String)
- `description` (String)
- `price` (BigDecimal)
- `stock_quantity` (Integer)
- `min_stock_level` (Integer)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)

### Stock Movement Table
- `id` (UUID, Primary Key)
- `product_id` (UUID, Foreign Key)
- `movement_type` (Enum: IN, OUT, ADJUSTMENT)
- `quantity` (Integer)
- `reason` (String)
- `order_id` (Long, nullable)
- `created_at` (Timestamp)

## Configuration

The service runs on port 8083 and connects to:
- PostgreSQL database on port 5434
- Kafka broker on localhost:9092

## Running the Service

1. Ensure PostgreSQL and Kafka are running (via docker-compose)
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Testing

Run tests with:
```bash
./mvnw test
```