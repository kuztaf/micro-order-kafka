package com.foodtruck.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private String category;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}