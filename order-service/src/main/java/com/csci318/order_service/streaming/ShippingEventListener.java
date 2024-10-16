package com.csci318.order_service.streaming;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.csci318.order_service.event.ShippingEvent;
import com.csci318.order_service.event.ShippingStatus;
import com.csci318.order_service.model.OrderStatus;
import com.csci318.order_service.service.OrderService;

@Service
public class ShippingEventListener {
    private final OrderService orderService;

    public ShippingEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public Consumer<ShippingEvent> processShipping() {
        return shippingEvent -> {
            if (shippingEvent.getEventType() == ShippingStatus.SHIPPED) {
                orderService.updateOrderStatus(shippingEvent.getOrderId(), OrderStatus.SHIPPED);
            } else if (shippingEvent.getEventType() == ShippingStatus.DELIVERED) {
                orderService.updateOrderStatus(shippingEvent.getOrderId(), OrderStatus.DELIVERED);
            }
        };
    }
}
