package csci318.shipping_service.service;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import csci318.shipping_service.model.Shipping;
import csci318.shipping_service.model.event.PaymentEvent;
import csci318.shipping_service.repository.ShippingRepository;
import csci318.shipping_service.stream.ShippingEventPublisher;

@Service
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final ShippingEventPublisher shippingEventPublisher;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ShippingService(ShippingRepository shippingRepository, ShippingEventPublisher shippingEventPublisher) {
        this.shippingRepository = shippingRepository;
        this.shippingEventPublisher = shippingEventPublisher;
    }


    public Shipping createShipping(PaymentEvent paymentEvent){

        Shipping s = convertShipping(paymentEvent);
        shippingRepository.save(s);

        shippingEventPublisher.createShipmentEvent(s);

        return s;
    }


    public Shipping convertShipping(PaymentEvent paymentEvent){

        return new Shipping(paymentEvent.getOrderId());
    }

    
}
