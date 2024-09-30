package csci318.demo.model.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_events")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "customer_id")
    private Long customerId;

    public enum EventType {
        USER_REGISTERED,
        USER_LOGGED_IN,
        CART_CREATED,
        PRODUCT_ADDED_TO_CART,
        PRODUCT_REMOVED_FROM_CART
    }

    public UserEvent() {}

    public UserEvent(EventType eventType, Long cartId, Long productId, Long customerId) {
        this.eventType = eventType;
        this.cartId = cartId;
        this.productId = productId;
        this.customerId = customerId;
    }


    public Long getId() {
        return id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

}

