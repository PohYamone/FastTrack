package com.csci318.order_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csci318.order_service.event.OrderEventType;
import com.csci318.order_service.model.Order;
import com.csci318.order_service.model.OrderItem;
import com.csci318.order_service.model.OrderStatus;
import com.csci318.order_service.model.DTO.OrderDTO;
import com.csci318.order_service.model.DTO.OrderItemDTO;
import com.csci318.order_service.repository.OrderRepository;
import com.csci318.order_service.streaming.OrderEventPublisher;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderEventPublisher orderEventPublisher) {
        this.orderEventPublisher = orderEventPublisher;
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = convertToOrder(orderDTO);
        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }
        Order savedOrder = orderRepository.save(order);
        orderEventPublisher.publishOrderEvent(order.getId(), order.getCustomerId(), OrderEventType.ORDER_CREATED);
        return convertToOrderDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order number " + orderId + " not found"));
        return convertToOrderDTO(order);
    }

    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::convertToOrderDTO).collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order number " + orderId + " not found"));
        orderRepository.delete(order);
        orderEventPublisher.publishOrderEvent(order.getId(), order.getCustomerId(), OrderEventType.ORDER_CANCELLED);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order number " + orderId + "  not found"));

        order.setStatus(status); // Update the status of the order
        orderRepository.save(order);

        if (status == OrderStatus.CONFIRMED) {
            orderEventPublisher.publishOrderEvent(order.getId(), order.getCustomerId(), OrderEventType.ORDER_CONFIRMED);
        } else if (status == OrderStatus.SHIPPED) {
            orderEventPublisher.publishOrderEvent(order.getId(), order.getCustomerId(), OrderEventType.ORDER_SHIPPED);
        } else if (status == OrderStatus.DELIVERED) {
            orderEventPublisher.publishOrderEvent(order.getId(), order.getCustomerId(), OrderEventType.ORDER_DELIVERED);
        }
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
        return order.getOrderItems().stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());
    }

    public OrderDTO addItemToOrder(Long orderId, OrderItemDTO newItemDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
        OrderItem newItem = convertToOrderItem(newItemDTO);
        newItem.setOrder(order);

        order.addOrderItem(newItem);
        Order updatedOrder = orderRepository.save(order);
        return convertToOrderDTO(updatedOrder);
    }

    public OrderDTO removeItemFromOrder(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        OrderItem itemToRemove = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order item not found!"));

        order.getOrderItems().remove(itemToRemove);
        Order updatedOrder = orderRepository.save(order);

        return convertToOrderDTO(updatedOrder);
    }

    // Convert Order entity to OrderDTO
    private OrderDTO convertToOrderDTO(Order order) {
        List<OrderItemDTO> orderItemsDTO = order.getOrderItems().stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toList());

        return new OrderDTO(order.getId(), order.getCustomerId(), order.getStatus(), orderItemsDTO);
    }

    // Convert OrderItem entity to OrderItemDTO
    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getId(), orderItem.getProductId(), orderItem.getQuantity(),
                orderItem.getPrice());
    }

    // Convert OrderItemDTO to OrderItem
    private OrderItem convertToOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemDTO.getProductId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        return orderItem;
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerId(orderDTO.getCustomerId());
        order.setStatus(orderDTO.getStatus());

        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        return order;
    }

}
