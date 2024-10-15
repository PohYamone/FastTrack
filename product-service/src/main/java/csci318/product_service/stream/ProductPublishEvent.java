package csci318.product_service.stream;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import csci318.product_service.model.event.ProductEvent;

@Service
public class ProductPublishEvent {

    private final StreamBridge streamBridge;

    public ProductPublishEvent(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventListener
    public void handleEvents(ProductEvent productEvent) {

        switch (productEvent.getType()) {
            case PRODUCT_CREATED:
                productCreateEvent(productEvent);
                break;
            case PRODUCT_UPDATED:
                productUpdateEvent(productEvent);
                break;
            case PRODUCT_DELETED:
                productDeleteEvent(productEvent);
                break;
        }
    }

    public void productCreateEvent(ProductEvent productEvent){
        System.out.println("Product Created with id " + productEvent.getId());
        streamBridge.send("product-create-out-0", productEvent);
    }

    public void productUpdateEvent(ProductEvent productEvent){

    }

    public void productDeleteEvent(ProductEvent productEvent){

        System.out.println("Product Deleted with id " + productEvent.getId());
        streamBridge.send("product-create-out-0", productEvent);

    }


    
}
