#!/bin/bash

# Script para crear 5 órdenes de ejemplo en order-service

echo "Creando 5 órdenes de ejemplo..."

# Orden 1: Pizza y Refresco
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST001",
    "items": ["Pizza Piña", "Coca-Cola"]
  }' \
  -s -o /dev/null -w "Orden 1 creada\n"

# Orden 2: Hamburguesa y Papas
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST002",
    "items": ["Hamburguesa", "Papas Fritas", "Agua"]
  }' \
  -s -o /dev/null -w "Orden 2 creada\n"

# Orden 3: Sushi
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST003",
    "items": ["Sushi Roll", "Té Verde"]
  }' \
  -s -o /dev/null -w "Orden 3 creada\n"

# Orden 4: Pasta y Vino
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST004",
    "items": ["Pasta Carbonara", "Vino Tinto"]
  }' \
  -s -o /dev/null -w "Orden 4 creada\n"

# Orden 5: Ensalada y Jugo
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "CUST005",
    "items": ["Ensalada César", "Jugo de Naranja"]
  }' \
  -s -o /dev/null -w "Orden 5 creada\n"

echo "¡Todas las órdenes han sido creadas!"
echo "Puedes verificarlas con: curl http://localhost:8081/api/orders"