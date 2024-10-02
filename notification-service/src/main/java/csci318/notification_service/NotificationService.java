package csci318.notification_service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    public void sendOrderNotification(String orderId, String userEmail) {
        System.out.println("Notification sent for Order ID: " + orderId + " to email: " + userEmail);
    }
}
