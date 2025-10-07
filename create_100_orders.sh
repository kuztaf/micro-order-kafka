#!/bin/bash

# Script para crear 100 órdenes de ejemplo en order-service

echo "Creando 100 órdenes de ejemplo..."

# Lista de items disponibles
items=("Pizza Piña" "Coca-Cola" "Hamburguesa" "Papas Fritas" "Agua" "Sushi Roll" "Té Verde" "Pasta Carbonara" "Vino Tinto" "Ensalada César" "Jugo de Naranja" "Tacos" "Burrito" "Nachos" "Quesadilla" "Café" "Té" "Helado" "Pastel" "Galletas")

# Función para seleccionar items aleatorios (1-3 items por orden)
get_random_items() {
    num_items=$((RANDOM % 3 + 1))  # 1 a 3 items
    selected_items=()
    for ((i=0; i<num_items; i++)); do
        item_index=$((RANDOM % ${#items[@]}))
        selected_items+=("\"${items[$item_index]}\"")
    done
    echo "$(IFS=,; echo "${selected_items[*]}")"
}

# Crear 100 órdenes
for i in $(seq 1 100); do
    customer_id=$(printf "CUST%03d" $i)
    order_items=$(get_random_items)
    
    curl -X POST http://localhost:8081/api/orders \
      -H "Content-Type: application/json" \
      -d "{
        \"customerId\": \"$customer_id\",
        \"items\": [$order_items]
      }" \
      -s -o /dev/null -w "Orden $i creada\n"
done

echo "¡Todas las 100 órdenes han sido creadas!"
echo "Puedes verificarlas con: curl http://localhost:8081/api/orders"