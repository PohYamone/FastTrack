package csci318.cart_service.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import csci318.cart_service.model.event.CartEvent;
import csci318.cart_service.repository.CartEventRepository;

@Service
public class EventHandler {

    private final CartEventRepository cartEventRepository;

    public EventHandler(CartEventRepository cartEventRepository) {
        this.cartEventRepository = cartEventRepository;
    }

    @TransactionalEventListener
    public void handleTransactionEvents(CartEvent cartEvent){

        switch (cartEvent.getEventType()) {
            case PRODUCT_ADDED_TO_CART:
                handleProductAddedEvent(cartEvent);
                break;
            case PRODUCT_REMOVED_FROM_CART:
                handleProductRemovedEvent(cartEvent);
                break;
            default:
                break;
        }

    }

    @EventListener
    private void handleCartCreatedEvent(CartEvent cartEvent) {
        if(cartEvent.getEventType() == CartEvent.EventType.CART_CREATED){
            System.out.println("Cart created for customer ID: " + cartEvent.getCustomerId() + " with cart ID: " + cartEvent.getCartId());
            cartEventRepository.save(cartEvent);
        }
    }

    private void handleProductAddedEvent(CartEvent cartEvent) {
        System.out.println("Product added to cart ID: " + cartEvent.getCartId() + " with product ID: " + cartEvent.getProductId());
        cartEventRepository.save(cartEvent);
    }

    private void handleProductRemovedEvent(CartEvent cartEvent) {
        System.out.println("Product removed from cart ID: " + cartEvent.getCartId() + " with product ID: " + cartEvent.getProductId());
        cartEventRepository.save(cartEvent);
    }
    
}
