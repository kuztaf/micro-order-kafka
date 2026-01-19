package com.foodtruck.inventory_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void consumeOrderEvent(ConsumerRecord<String, String> record) {
        String orderIdStr = record.key();
        String eventType = record.value();

        log.info("Received order event: orderId={}, eventType={}", orderIdStr, eventType);

        if ("ORDER_CREATED".equals(eventType)) {
            try {
                Long orderId = Long.valueOf(orderIdStr);

                // For now, we'll simulate reserving stock for common items
                // In a real scenario, you'd get the order details from order-service
                // or have the order event include item details

                // Example: Reserve stock for pizza and coke (common items)
                try {
                    inventoryService.reserveStock(orderId, "PIZZA001", 1);
                } catch (Exception e) {
                    log.warn("Could not reserve pizza stock for order {}: {}", orderId, e.getMessage());
                }

                try {
                    inventoryService.reserveStock(orderId, "COKE001", 1);
                } catch (Exception e) {
                    log.warn("Could not reserve coke stock for order {}: {}", orderId, e.getMessage());
                }

                log.info("Processed order creation for order {}", orderId);

            } catch (NumberFormatException e) {
                log.error("Invalid order ID format: {}", orderIdStr);
            } catch (Exception e) {
                log.error("Error processing order event: {}", e.getMessage(), e);
            }
        }
    }
}