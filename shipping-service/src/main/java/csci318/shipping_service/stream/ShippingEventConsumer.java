package csci318.shipping_service.stream;

import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import csci318.shipping_service.model.event.PaymentEvent;
import csci318.shipping_service.model.event.PaymentStatus;

@Service
public class ShippingEventConsumer {

    private final StreamBridge streamBridge;

    public ShippingEventConsumer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


     @Bean
    public Consumer<PaymentEvent> processPayment() {
        return paymentEvent -> {
            
            if(paymentEvent.getStatus() == PaymentStatus.SUCCESS){
                System.out.println("I am receiving payment event" + paymentEvent);
            }
            
        };
    }

    
}
