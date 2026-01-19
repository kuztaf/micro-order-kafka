package com.foodtruck.inventory_service.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }

    public ProductNotFoundException(String sku, boolean isSku) {
        super("Product not found with SKU: " + sku);
    }
}