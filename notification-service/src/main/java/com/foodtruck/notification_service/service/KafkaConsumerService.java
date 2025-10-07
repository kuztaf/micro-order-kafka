package com.foodtruck.notification_service.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final NotificationService notificationService;

    public KafkaConsumerService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        Long orderId = Long.valueOf(record.key());
        String eventType = record.value();
        notificationService.sendNotification(orderId, eventType);
    }
}