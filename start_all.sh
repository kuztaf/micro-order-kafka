#!/bin/bash

# Script para levantar todos los microservicios con Docker Compose

echo "🚀 Levantando infraestructura completa de Food Truck..."

# Levantar todos los servicios
docker-compose up -d

echo "⏳ Esperando a que todos los servicios estén listos..."
echo "   - PostgreSQL databases: ~30 segundos"
echo "   - Kafka + Zookeeper: ~20 segundos"
echo "   - Microservicios Spring Boot: ~60-90 segundos"
echo ""

# Esperar un poco y mostrar estado
sleep 10
echo "📊 Estado de los servicios:"
docker-compose ps

echo ""
echo "✅ Servicios disponibles:"
echo "   🌐 Kafka UI: http://localhost:8080"
echo "   📦 Order Service: http://localhost:8081"
echo "   📢 Notification Service: http://localhost:8082"
echo "   📦 Inventory Service: http://localhost:8083"
echo ""
echo "🧪 Para probar:"
echo "   ./create_orders.sh"
echo "   ./create_inventory.sh"
echo ""
echo "🛑 Para detener: docker-compose down"