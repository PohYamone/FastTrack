package cscsi318.demoClient.dto;

import java.util.List;

public class OrderDTO {
    private Long id;
    private Long customerId;
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long customerId, OrderStatus status, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.customerId = customerId;
        this.status = (status != null) ? status : OrderStatus.PENDING;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
