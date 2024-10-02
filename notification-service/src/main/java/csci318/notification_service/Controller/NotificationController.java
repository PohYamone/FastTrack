package csci318.notification_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import csci318.notification_service.Model.NotificationService;


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
}