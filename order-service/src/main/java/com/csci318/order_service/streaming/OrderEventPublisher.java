package com.csci318.order_service.streaming;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.csci318.order_service.event.OrderEvent;
import com.csci318.order_service.event.OrderEventType;

@Service
public class OrderEventPublisher {
    private final StreamBridge streamBridge;

    @Value("${spring.cloud.stream.bindings.orderEvent-out-0.destination}")
    private String orderEventsTopic;

    public OrderEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrderEvent(Long orderId, Long customerId, OrderEventType eventType) {
        OrderEvent orderEvent = new OrderEvent(orderId, customerId, eventType, LocalDateTime.now());
        streamBridge.send(orderEventsTopic, orderEvent);
    }
}
