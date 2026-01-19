package com.example.inventory_service.entity;

public enum MovementType {
    RESERVED,      // Stock reservado para un pedido
    RELEASED,      // Stock liberado por cancelación
    SOLD,          // Stock confirmado como vendido
    RESTOCKED,     // Stock añadido al inventario
    ADJUSTMENT     // Ajuste manual
}