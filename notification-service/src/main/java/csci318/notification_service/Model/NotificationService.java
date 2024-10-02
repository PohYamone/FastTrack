package csci318.notification_service.Model;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    public void sendOrderNotification(String orderId, String userEmail) {
        System.out.println("Notification sent for Order ID: " + orderId + " to email: " + userEmail);
    }
    //Order Notification
    public void sendOrderPlaceNotification(String orderId, String userEmail){
        System.out.println("Order "+ orderId + " has been placed. Notification send to " + userEmail);
    }
    public void sendOrderShippedNotification(String orderId, String trackingUrl, String userEmail) {
        System.out.println("Your order " + orderId + " has been shipped. Track it here: " + trackingUrl);
    }
    
    public void sendOrderDeliveredNotification(String orderId, String userEmail) {
        System.out.println("Your order " + orderId + " has been delivered. Notification sent to " + userEmail);
    }
    //Payment Notification
    public void sendPaymentSuccessNotification(String orderId, String userEmail) {
        System.out.println("Payment for Order " + orderId + " has been processed successfully. Notification sent to " + userEmail);
    }
    
    public void sendPaymentFailureNotification(String orderId, String userEmail) {
        System.out.println("Payment for Order " + orderId + " failed. Please retry. Notification sent to " + userEmail);
    }
    
    public void sendRefundIssuedNotification(String orderId, String userEmail) {
        System.out.println("A refund has been issued for Order " + orderId + ". Notification sent to " + userEmail);
    }
    //delivery delay
    public void sendDeliveryDelayNotification(String orderId, String userEmail) {
        System.out.println("Your order " + orderId + " has been delayed. We apologize for the inconvenience.");
    }
}
