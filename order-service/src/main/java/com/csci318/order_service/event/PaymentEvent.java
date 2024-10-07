package com.csci318.order_service.event;

import java.io.Serializable;

public class PaymentEvent implements Serializable {
    private Long paymentId;
    private Long orderId;
    private PaymentStatus status;
    private String timestamp;

    public PaymentEvent(Long paymentId, Long orderId, PaymentStatus status, String timestamp) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
