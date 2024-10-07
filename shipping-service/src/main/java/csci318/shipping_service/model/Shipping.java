package csci318.shipping_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Address deliverAddress;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    private LocalDateTime shippingDate;

    public Shipping() {
    }

    public Shipping(Long orderId) {
        this.orderId = orderId;
        this.status = ShippingStatus.PENDING;
        this.shippingDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Address getAddress() {
        return deliverAddress;
    }

    public void setAddress(Address deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }
}

