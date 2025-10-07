package com.foodtruck.order_service.dto;

import java.util.List;

public class OrderResponse {
    private Long id;
    private String customerId;
    private List<String> items;
    private String status;

    public OrderResponse() {}

    public OrderResponse(Long id, String customerId, List<String> items, String status) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}