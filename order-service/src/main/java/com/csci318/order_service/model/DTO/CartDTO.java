package com.csci318.order_service.model.DTO;

import java.util.Date;
import java.util.List;

public class CartDTO {

    private Long id;
    private Long customerId;
    private Date creationDate;
    private List<CartItemDTO> items;

    public Date getDate() {
        return creationDate;
    }

    public void setDate(Date d) {
        this.creationDate = d;
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

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
