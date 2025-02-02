package csci318.payment_service.streaming;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import csci318.payment_service.event.PaymentEvent;
import csci318.payment_service.model.PaymentStatus;

@Service
public class PaymentEventPublisher {
    private final StreamBridge streamBridge;

    public PaymentEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishPaymentEvent(Long paymentId, Long orderId, Long customerId, PaymentStatus status,
            String timestamp) {
        PaymentEvent paymentEvent = new PaymentEvent(paymentId, orderId, customerId, status, timestamp);
        streamBridge.send("paymentEvent-out-0", paymentEvent);
        System.out.println("PAY PUBLISHED");
    }

}
