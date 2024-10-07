package csci318.shipping_service.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csci318.shipping_service.service.ShippingService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }








}
