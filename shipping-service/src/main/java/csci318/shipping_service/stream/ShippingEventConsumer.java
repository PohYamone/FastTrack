package csci318.shipping_service.stream;

import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import csci318.shipping_service.model.event.PaymentEvent;
import csci318.shipping_service.model.event.PaymentStatus;
import csci318.shipping_service.service.ShippingService;

@Service
public class ShippingEventConsumer {

    private final ShippingService shippingService;

    public ShippingEventConsumer(ShippingService shippingService) {
        this.shippingService = shippingService;
    }


     @Bean
    public Consumer<PaymentEvent> processPayment() {
        return paymentEvent -> {
            if(paymentEvent.getStatus() == PaymentStatus.SUCCESS){
                shippingService.createShipping(paymentEvent);
            }
            
        };
    }

    
}
