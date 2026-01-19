package com.foodtruck.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockCheckResponse {

    private String sku;
    private String name;
    private Integer requestedQuantity;
    private Integer availableQuantity;
    private Boolean sufficientStock;
    private String message;
}