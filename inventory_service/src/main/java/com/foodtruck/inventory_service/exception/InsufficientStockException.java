package com.foodtruck.inventory_service.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String sku, Integer requested, Integer available) {
        super(String.format("Insufficient stock for product %s. Requested: %d, Available: %d",
              sku, requested, available));
    }
}