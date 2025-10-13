package com.foodtruck.notification_service.event;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderEvent {
    private Long orderId;
    private String eventType;

}