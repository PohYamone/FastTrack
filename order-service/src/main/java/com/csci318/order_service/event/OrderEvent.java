package com.csci318.order_service.event;

import java.time.LocalDateTime;

public class OrderEvent {
    private Long orderId;
    private Long customerId;
    private OrderEventType eventType;
    private LocalDateTime timestamp;

    public OrderEvent(Long orderId, Long customerId, OrderEventType eventType, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public OrderEventType getEventType() {
        return this.eventType;
    }

    public void setEventType(OrderEventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
