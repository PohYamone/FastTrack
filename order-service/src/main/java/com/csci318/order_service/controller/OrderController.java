package com.csci318.order_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csci318.order_service.model.OrderStatus;
import com.csci318.order_service.model.DTO.OrderDTO;
import com.csci318.order_service.model.DTO.OrderItemDTO;
import com.csci318.order_service.service.OrderService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order
    @PostMapping("/{cartId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long cartId) {
        OrderDTO newOrder = orderService.createOrder(cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // Get all orders by customer ID
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderDTO> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderId, status); // Update the order status
        return ResponseEntity.ok().build();
    }

    // Get all items of an order by order ID
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemDTO> orderItems = orderService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }

    // Add item to an existing order
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderDTO> addItemToOrder(@PathVariable Long orderId, @RequestBody OrderItemDTO newItem) {
        OrderDTO updatedOrder = orderService.addItemToOrder(orderId, newItem);
        return ResponseEntity.ok(updatedOrder);
    }

    // Remove item from an existing order
    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        orderService.removeItemFromOrder(orderId, itemId);
        return ResponseEntity.noContent().build();
    }
}