package csci318.payment_service.service;

import org.springframework.stereotype.Service;
import csci318.payment_service.model.Payment;
import csci318.payment_service.model.PaymentStatus;
import csci318.payment_service.repository.PaymentRepository;
import csci318.payment_service.streaming.PaymentEventPublisher;
import csci318.payment_service.model.DTO.PaymentDTO;

import java.util.List;
import java.util.stream.Collectors;

import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public PaymentService(PaymentRepository paymentRepository, PaymentEventPublisher paymentEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    // Create a new payment
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment(
                paymentDTO.getOrderId(),
                paymentDTO.getCustomerId(),
                paymentDTO.getAmount(),
                PaymentStatus.PENDING // Default status
        );

        Payment savedPayment = paymentRepository.save(payment);

        paymentEventPublisher.publishPaymentEvent(payment.getId(), payment.getOrderId(), payment.getCustomerId(),
                payment.getStatus(),
                payment.getPaymentDate().format(formatter));

        return convertToDTO(savedPayment);
    }

    // Get a specific payment by paymentId
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        return convertToDTO(payment);
    }

    // Get all payments by orderId
    public List<PaymentDTO> getPaymentsByOrderId(Long orderId) {
        List<Payment> payments = paymentRepository.findByOrderId(orderId);
        return payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        payment.setStatus(status); // Update the payment status
        paymentRepository.save(payment);

        paymentEventPublisher.publishPaymentEvent(payment.getId(), payment.getOrderId(), payment.getCustomerId(),
                status,
                payment.getPaymentDate().format(formatter));
    }

    // Convert Payment entity to PaymentDTO
    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(payment.getOrderId());
        paymentDTO.setCustomerId(payment.getCustomerId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentDate(payment.getPaymentDate().format(formatter));
        return paymentDTO;
    }
}
