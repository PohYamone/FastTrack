package csci318.shipping_service.stream;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import csci318.shipping_service.model.Shipping;

@Service
public class ShippingEventPublisher {

    private final StreamBridge streamBridge;

    public ShippingEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    public void createShipmentEvent(Shipping s){
        
        System.out.println(s.getStatus());

        
    }

    public void updateShipmentEvent(Shipping s){

        System.out.println(s.getStatus());
        
    }




}
