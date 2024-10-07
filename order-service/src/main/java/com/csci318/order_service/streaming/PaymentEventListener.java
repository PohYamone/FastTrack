package com.csci318.order_service.streaming;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.csci318.order_service.event.PaymentEvent;
import com.csci318.order_service.event.PaymentStatus;
import com.csci318.order_service.model.OrderStatus;
import com.csci318.order_service.model.DTO.OrderDTO;
import com.csci318.order_service.service.OrderService;

@Service
public class PaymentEventListener {
    private final OrderService orderService;
    private final RestTemplate restTemplate;

    public PaymentEventListener(OrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
    }

    @Bean
    public Consumer<PaymentEvent> processPayment() {
        return paymentEvent -> {
            System.out.println("I am receiving payment event" + paymentEvent);
            if (paymentEvent.getStatus() == PaymentStatus.SUCCESS) {
                // Update the order status to CONFIRMED if payment is successful
                orderService.updateOrderStatus(paymentEvent.getOrderId(), OrderStatus.CONFIRMED);
                OrderDTO order = orderService.getOrderById(paymentEvent.getOrderId());

                order.getOrderItems().forEach(orderItem -> {
                    String updateStockURL = "http://localhost:8084/api/inventory/products/" + orderItem.getProductId()
                            + "/stock?amount=" + (-orderItem.getQuantity());
                    restTemplate.put(updateStockURL, null);
                });

            } else if (paymentEvent.getStatus() == PaymentStatus.FAIL) {
                // Handle payment failure, potentially cancel the order
                orderService.updateOrderStatus(paymentEvent.getOrderId(), OrderStatus.CANCELLED);
            }
        };
    }

}
