package csci318.notification_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/notifications")
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