#!/bin/bash

# Script para crear productos de ejemplo en inventory-service

echo "Creando productos de ejemplo..."

# Producto 1: Pizza
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "PIZZA001",
    "name": "Pizza Margherita",
    "description": "Pizza clásica con queso mozzarella y salsa de tomate",
    "price": 12.99,
    "stockQuantity": 50,
    "minStockLevel": 10
  }' \
  -s -o /dev/null -w "Producto 1 creado: Pizza Margherita\n"

# Producto 2: Hamburguesa
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "BURGER001",
    "name": "Hamburguesa Clásica",
    "description": "Hamburguesa con carne, lechuga, tomate y queso",
    "price": 8.99,
    "stockQuantity": 30,
    "minStockLevel": 5
  }' \
  -s -o /dev/null -w "Producto 2 creado: Hamburguesa Clásica\n"

# Producto 3: Papas Fritas
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "FRIES001",
    "name": "Papas Fritas Grandes",
    "description": "Porción grande de papas fritas doradas",
    "price": 4.99,
    "stockQuantity": 100,
    "minStockLevel": 20
  }' \
  -s -o /dev/null -w "Producto 3 creado: Papas Fritas Grandes\n"

# Producto 4: Coca-Cola
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "DRINK001",
    "name": "Coca-Cola 330ml",
    "description": "Refresco Coca-Cola en lata de 330ml",
    "price": 2.50,
    "stockQuantity": 200,
    "minStockLevel": 50
  }' \
  -s -o /dev/null -w "Producto 4 creado: Coca-Cola 330ml\n"

# Producto 5: Sushi Roll
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "SUSHI001",
    "name": "Sushi Roll California",
    "description": "Roll de sushi con cangrejo, aguacate y pepino",
    "price": 15.99,
    "stockQuantity": 25,
    "minStockLevel": 5
  }' \
  -s -o /dev/null -w "Producto 5 creado: Sushi Roll California\n"

# Producto 6: Pasta Carbonara
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "PASTA001",
    "name": "Pasta Carbonara",
    "description": "Pasta italiana con salsa carbonara tradicional",
    "price": 11.99,
    "stockQuantity": 40,
    "minStockLevel": 8
  }' \
  -s -o /dev/null -w "Producto 6 creado: Pasta Carbonara\n"

# Producto 7: Ensalada César
curl -X POST http://localhost:8083/api/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "SALAD001",
    "name": "Ensalada César",
    "description": "Ensalada fresca con aderezo César y crutones",
    "price": 7.99,
    "stockQuantity": 35,
    "minStockLevel": 10
  }' \
  -s -o /dev/null -w "Producto 7 creado: Ensalada César\n"

echo "Todos los productos de ejemplo han sido creados exitosamente!"