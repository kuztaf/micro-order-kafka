package com.foodtruck.inventory_service.controller;

import com.foodtruck.inventory_service.dto.InventoryRequest;
import com.foodtruck.inventory_service.dto.InventoryResponse;
import com.foodtruck.inventory_service.dto.StockCheckResponse;
import com.foodtruck.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createProduct(@Valid @RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<InventoryResponse> updateProduct(
            @PathVariable String sku,
            @Valid @RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.updateProduct(sku, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllProducts() {
        List<InventoryResponse> products = inventoryService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryResponse> getProductBySku(@PathVariable String sku) {
        InventoryResponse product = inventoryService.getProductBySku(sku);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/check/{sku}")
    public ResponseEntity<StockCheckResponse> checkStock(
            @PathVariable String sku,
            @RequestParam Integer quantity) {
        StockCheckResponse response = inventoryService.checkStock(sku, quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> getLowStockProducts(
            @RequestParam(defaultValue = "10") Integer threshold) {
        List<InventoryResponse> products = inventoryService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }
}