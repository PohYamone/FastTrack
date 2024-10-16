package csci318.analytics_service.model.event;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderEvent implements Serializable {
    private Long orderId;
    private Long customerId;
    private OrderEventType eventType;
    private LocalDateTime timestamp;

    public OrderEvent(){};
    
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
