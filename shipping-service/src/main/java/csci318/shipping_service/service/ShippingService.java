package csci318.shipping_service.service;

import java.text.NumberFormat.Style;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import csci318.shipping_service.model.Shipping;
import csci318.shipping_service.model.dto.Customer;
import csci318.shipping_service.model.event.OrderEvent;
import csci318.shipping_service.model.event.PaymentEvent;
import csci318.shipping_service.model.event.ShippingEvent;
import csci318.shipping_service.repository.ShippingRepository;
import csci318.shipping_service.stream.ShippingEventPublisher;

@Service
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final ShippingEventPublisher shippingEventPublisher;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RestTemplate restTemplate;


    public ShippingService(ShippingRepository shippingRepository, ShippingEventPublisher shippingEventPublisher, ApplicationEventPublisher applicationEventPublisher, RestTemplate restTemplate) {
        this.shippingRepository = shippingRepository;
        this.shippingEventPublisher = shippingEventPublisher;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void updateShipping(Long shippingId){

        Shipping ship = shippingRepository.findByShippingId(shippingId);
        ship.nextStatus();
        ship.setShippingDate(LocalDateTime.now());

        shippingRepository.save(ship);
        ShippingEvent shippingEvent = new ShippingEvent(ship.getOrderId(),ship.getId(),ship.getStatus(),ship.getShippingDate());

        applicationEventPublisher.publishEvent(shippingEvent);

    }

    public Shipping createShipping(PaymentEvent paymentEvent){

        Shipping s = convertShipping(paymentEvent);
        shippingRepository.save(s);

        return s;
    }

    public List<Shipping> listShipping(){
        return shippingRepository.findAll();
    }


    public Shipping convertShipping(PaymentEvent paymentEvent){

        String ServiceURL = "http://localhost:8080/api/users/" + paymentEvent.getCustomerId();
        Customer customer = restTemplate.getForObject(ServiceURL, Customer.class);

        return new Shipping(paymentEvent.getOrderId(), customer.getId(), customer.getAddress());
    }


    public Shipping getShippingId(OrderEvent orderEvent){
        return shippingRepository.findByOrderId(orderEvent.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Shipping not found for orderId: " + orderEvent.getOrderId()));
    }

    
}
