package com.foodtruck.order_service.service;

import com.foodtruck.order_service.dto.OrderRequest;
import com.foodtruck.order_service.dto.OrderResponse;
import com.foodtruck.order_service.entity.Order;
import com.foodtruck.order_service.event.OrderEvent;
import com.foodtruck.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;

    public OrderService(OrderRepository orderRepository, KafkaProducerService kafkaProducerService) {
        this.orderRepository = orderRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order(request.getCustomerId(), request.getItems(), "CREATED");
        order = orderRepository.save(order);
        kafkaProducerService.sendOrderEvent(new OrderEvent(order.getId(), "ORDER_CREATED"));
        return new OrderResponse(order.getId(), order.getCustomerId(), order.getItems(), order.getStatus());
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return new OrderResponse(order.getId(), order.getCustomerId(), order.getItems(), order.getStatus());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(o -> new OrderResponse(o.getId(), o.getCustomerId(), o.getItems(), o.getStatus()))
                .collect(Collectors.toList());
    }
}