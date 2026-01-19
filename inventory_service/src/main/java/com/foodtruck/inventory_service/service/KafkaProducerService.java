package com.foodtruck.inventory_service.service;

import com.foodtruck.inventory_service.event.InventoryReleasedEvent;
import com.foodtruck.inventory_service.event.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendInventoryReservedEvent(Long orderId, String sku, Integer quantity) {
        InventoryReservedEvent event = InventoryReservedEvent.builder()
                .orderId(orderId)
                .sku(sku)
                .quantity(quantity)
                .build();

        kafkaTemplate.send("inventory-events", orderId.toString(), event.getEventType());
        log.info("Sent inventory reserved event: orderId={}, sku={}, quantity={}",
                orderId, sku, quantity);
    }

    public void sendInventoryReleasedEvent(Long orderId, String sku, Integer quantity) {
        InventoryReleasedEvent event = InventoryReleasedEvent.builder()
                .orderId(orderId)
                .sku(sku)
                .quantity(quantity)
                .build();

        kafkaTemplate.send("inventory-events", orderId.toString(), event.getEventType());
        log.info("Sent inventory released event: orderId={}, sku={}, quantity={}",
                orderId, sku, quantity);
    }
}