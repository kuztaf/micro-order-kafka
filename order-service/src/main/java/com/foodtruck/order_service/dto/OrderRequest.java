package com.foodtruck.order_service.dto;

import java.util.List;

public class OrderRequest {
    private String customerId;
    private List<String> items;

    public OrderRequest() {}

    public OrderRequest(String customerId, List<String> items) {
        this.customerId = customerId;
        this.items = items;
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
}