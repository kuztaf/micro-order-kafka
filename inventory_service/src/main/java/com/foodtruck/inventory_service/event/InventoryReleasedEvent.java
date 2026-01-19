package com.foodtruck.inventory_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReleasedEvent {

    private Long orderId;
    private String sku;
    private Integer quantity;

    @Builder.Default
    private String eventType = "INVENTORY_RELEASED";
}