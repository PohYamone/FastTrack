package csci318.shipping_service.stream;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import csci318.shipping_service.model.Shipping;
import csci318.shipping_service.model.event.ShippingEvent;

@Service
public class ShippingEventPublisher {

    private final StreamBridge streamBridge;

    public ShippingEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    public void createShipmentEvent(Shipping s){
        
        System.out.println(s.getStatus());
        
    }

    @EventListener
    public void updateShipmentEvent(ShippingEvent s){

            streamBridge.send("shippingEvent-out-0", s);
            System.out.println(s.getEventType());
               
    }




}
