package csci318.cart_service.model.event;

import java.io.Serializable;

public class UserEvent implements Serializable{

    private Long id;
    private EventType eventType;
    private Long cartId;
    private Long productId;
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


