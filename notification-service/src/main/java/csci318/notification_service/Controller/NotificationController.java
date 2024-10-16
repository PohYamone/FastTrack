package csci318.notification_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import csci318.notification_service.Service.NotificationService;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(
            @RequestParam String orderId,
            @RequestParam String userEmail) {
        notificationService.sendOrderNotification(orderId, userEmail);
        return ResponseEntity.ok().build();
    }

       // Endpoint for order placed notification
       @PostMapping("/order-placed")
       public ResponseEntity<Void> sendOrderPlaceNotification(
               @RequestParam String orderId,
               @RequestParam String userEmail) {
           notificationService.sendOrderPlaceNotification(orderId, userEmail);
           return ResponseEntity.ok().build();
       }
   
       // Endpoint for order shipped notification
       @PostMapping("/order-shipped")
       public ResponseEntity<Void> sendOrderShippedNotification(
               @RequestParam String orderId,
               @RequestParam String trackingUrl,
               @RequestParam String userEmail) {
           notificationService.sendOrderShippedNotification(orderId, trackingUrl, userEmail);
           return ResponseEntity.ok().build();
       }
   
       // Endpoint for order delivered notification
       @PostMapping("/order-delivered")
       public ResponseEntity<Void> sendOrderDeliveredNotification(
               @RequestParam String orderId,
               @RequestParam String userEmail) {
           notificationService.sendOrderDeliveredNotification(orderId, userEmail);
           return ResponseEntity.ok().build();
       }
   
       // Endpoint for payment success notification
       @PostMapping("/payment-success")
       public ResponseEntity<Void> sendPaymentSuccessNotification(
               @RequestParam String orderId,
               @RequestParam String userEmail) {
           notificationService.sendPaymentSuccessNotification(orderId, userEmail);
           return ResponseEntity.ok().build();
       }
   
       // Endpoint for payment failure notification
       @PostMapping("/payment-failure")
       public ResponseEntity<Void> sendPaymentFailureNotification(
               @RequestParam String orderId,
               @RequestParam String userEmail) {
           notificationService.sendPaymentFailureNotification(orderId, userEmail);
           return ResponseEntity.ok().build();
       }
   
       // Endpoint for refund issued notification
       @PostMapping("/refund-issued")
       public ResponseEntity<Void> sendRefundIssuedNotification(
               @RequestParam String orderId,
               @RequestParam String userEmail) {
           notificationService.sendRefundIssuedNotification(orderId, userEmail);
           return ResponseEntity.ok().build();
       }
   
       // Endpoint for delivery delay notification
       @PostMapping("/delivery-delay")
       public ResponseEntity<Void> sendDeliveryDelayNotification(
               @RequestParam String orderId,
               @RequestParam String userEmail) {
           notificationService.sendDeliveryDelayNotification(orderId, userEmail);
           return ResponseEntity.ok().build();
               }
}