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
    private Long shippingId;
    private Long customerId;
    private Long orderId;
    private Address deliverAddress;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    private LocalDateTime shippingDate;

    public Shipping(){};
    
    public Shipping(Long orderId, Long customerId, Address deliAddress) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliverAddress = deliAddress;
        this.status = ShippingStatus.PENDING;
        this.shippingDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getCustomerId(){
        return customerId;
    }

    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }

    public Address getAddress() {
        return deliverAddress;
    }

    public void setAddress(Address deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public Long getId() {
        return shippingId;
    }

    public void setId(Long id) {
        this.shippingId = id;
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

    public void nextStatus() {
        ShippingStatus[] statuses = ShippingStatus.values();
        int currentIndex = this.status.ordinal();

        // Ensure we don't go out of bounds
        if (currentIndex < statuses.length - 1) {
            this.status = statuses[currentIndex + 1];
        } else {
            throw new IllegalStateException("No next status available");
        }
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "id=" + shippingId +
                ", customerId=" + customerId +
                ", orderId=" + orderId +
                ", deliverAddress=" + deliverAddress +
                ", status=" + status +
                ", shippingDate=" + shippingDate +
                '}';
    }


}

