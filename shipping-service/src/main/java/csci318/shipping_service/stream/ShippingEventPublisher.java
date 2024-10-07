package csci318.shipping_service.stream;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class ShippingEventPublisher {

    private final StreamBridge streamBridge;

    public ShippingEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }



    
}
