package com.csci318.order_service.event;

import java.time.LocalDateTime;

public class ShippingEvent {

    private Long shippingId;
    private Long orderId;
    private ShippingStatus eventType;
    private LocalDateTime timestamp;

    public ShippingEvent() {
    };

    public ShippingEvent(Long orderId, Long shippingId, ShippingStatus eventType, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.shippingId = shippingId;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShippingId() {
        return this.shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public ShippingStatus getEventType() {
        return this.eventType;
    }

    public void setEventType(ShippingStatus eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
