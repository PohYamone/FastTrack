package com.csci318.order_service.streaming;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.csci318.order_service.event.PaymentEvent;
import com.csci318.order_service.event.PaymentStatus;
import com.csci318.order_service.model.OrderStatus;
import com.csci318.order_service.service.OrderService;

@Service
public class PaymentEventListener {
    private final OrderService orderService;

    public PaymentEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public Consumer<PaymentEvent> processPayment() {
        return paymentEvent -> {
            System.out.println("I am receiving payment event" + paymentEvent);
            if (paymentEvent.getStatus() == PaymentStatus.SUCCESS) {
                // Update the order status to CONFIRMED if payment is successful
                orderService.updateOrderStatus(paymentEvent.getOrderId(), OrderStatus.CONFIRMED);
            } else if (paymentEvent.getStatus() == PaymentStatus.FAIL) {
                // Handle payment failure, potentially cancel the order
                orderService.updateOrderStatus(paymentEvent.getOrderId(), OrderStatus.CANCELLED);
            }
        };
    }

}
