# Food Truck Microservices

Microservices system for food truck order management using event-driven architecture with Kafka.

## Architecture

- **order-service**: REST service for creating and querying orders. Publishes order creation events to Kafka.
- **notification-service**: Service that consumes Kafka events and generates notifications, storing them in database.
- **inventory-service**: Service that manages product stock levels and handles inventory operations. Consumes order events to reserve/release inventory.

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
- PostgreSQL for inventory (port 5434)
- **Order Service** (Spring Boot, port 8081)
- **Notification Service** (Spring Boot, port 8082)
- **Inventory Service** (Spring Boot, port 8083)
- Kafka UI for monitoring (port 8080)

## How to Run

### Opción 1: Docker Compose (Recomendado - Todo Automatizado)

```bash
# Levantar todos los servicios automáticamente
./start_all.sh

# O manualmente:
docker-compose up -d
```

**¿Qué incluye?**
- ✅ Kafka + Zookeeper
- ✅ 3 bases de datos PostgreSQL (orders, notifications, inventory)
- ✅ 3 microservicios Spring Boot (order, notification, inventory)
- ✅ Kafka UI para monitoreo
- ✅ Health checks automáticos
- ✅ Perfiles Docker optimizados

**Servicios disponibles:**
- 🌐 Kafka UI: http://localhost:8080
- 📦 Order Service: http://localhost:8081
- 📢 Notification Service: http://localhost:8082
- 📦 Inventory Service: http://localhost:8083

### Opción 2: Desarrollo Local (Manual)

1. **Start infrastructure:**
   ```bash
   docker-compose up -d kafka zookeeper postgres-orders postgres-notifications postgres-inventory kafka-ui
   ```

2. **Run order-service:**
   ```bash
   cd order-service
   mvn spring-boot:run
   ```

3. **Run notification-service:**
   ```bash
   cd notification-service
   mvn spring-boot:run
   ```

4. **Run inventory-service:**
   ```bash
   cd inventory-service
   mvn spring-boot:run
   ```

## Test Scripts

- `start_all.sh`: Levanta todos los servicios con Docker Compose
- `create_orders.sh`: Creates 5 sample orders.
- `create_100_orders.sh`: Creates 100 sample orders.
- `create_inventory.sh`: Creates 7 sample products in inventory.

Run: `./start_all.sh`, `./create_orders.sh`, `./create_100_orders.sh`, or `./create_inventory.sh`

## Event Flow

1. Client creates order via POST to order-service.
2. order-service saves to DB and publishes "ORDER_PLACED" event to Kafka.
3. inventory-service consumes the event, reserves inventory for order items, and publishes inventory events.
4. notification-service consumes the event, creates notification and saves to DB.

## Inventory Management

The inventory-service automatically:
- Reserves stock when orders are placed
- Releases stock when orders are cancelled
- Tracks stock movements with audit trail
- Provides REST API for inventory management
- Sends alerts for low stock levels

## Monitoring

- Service logs to see Kafka consumption.
- Kafka UI to view messages and consumers.
- DB queries to verify data.