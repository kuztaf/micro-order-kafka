package com.foodtruck.notification_service.service;

import com.foodtruck.notification_service.entity.Notification;
import com.foodtruck.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(Long orderId, String eventType) {
        String message = "Order " + orderId + " has event: " + eventType;
        Notification notification = new Notification(orderId, message, "SENT");
        notificationRepository.save(notification);
        // In real app, send email or SMS
        System.out.println("Notification sent: " + message);
    }
}