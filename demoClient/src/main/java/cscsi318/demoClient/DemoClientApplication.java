package cscsi318.demoClient;

import java.util.Random;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import cscsi318.demoClient.dto.OrderDTO;
import cscsi318.demoClient.dto.PaymentDTO;
import cscsi318.demoClient.dto.PaymentStatus;

public class DemoClientApplication {

    public static void main(String[] args) throws InterruptedException {

        final String orderUrl = "http://localhost:9090/api/orders/{cartId}";
        final String paymentUrl = "http://localhost:8087/api/payments/{paymentId}/status";
        final String shippingUrl = "http://localhost:8089/api/shipping/{shippingId}/update";
        Long cartId = 1L;

        Random rand = new Random();
        int upperbound = 3;
        PaymentStatus newStatus = PaymentStatus.SUCCESS;

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        
        // Initialize `i` outside the while loop to maintain its state across iterations
        int i = 1;
		int a = 1;

        while (true) {
            
            int orders = rand.nextInt(upperbound) + 1;

            // First loop for orders and payments
            for (int j = 0; j < orders; j++, i++) {  

                // 1. Create an order
                ResponseEntity<OrderDTO> response = restTemplate.postForEntity(orderUrl, null, OrderDTO.class, cartId);

                // 2. Create a payment associated with the order
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setOrderId((long) i);  
                paymentDTO.setCustomerId(1L); 
                paymentDTO.setAmount(100.00);  

                ResponseEntity<PaymentDTO> paymentResponse = restTemplate.postForEntity("http://localhost:8087/api/payments", paymentDTO, PaymentDTO.class);

              
                Long paymentId = paymentResponse.getBody().getOrderId(); 

                // 3. Update the payment status
                String paymentUrlWithParams = UriComponentsBuilder
                        .fromUriString(paymentUrl)
                        .queryParam("status", newStatus)  
                        .buildAndExpand(paymentId)  
                        .toUriString();

                // Send the PATCH request to update the payment status
                ResponseEntity<Void> paymentUpdateResponse = restTemplate.exchange(
                        paymentUrlWithParams,
                        HttpMethod.PATCH,
                        null, 
                        Void.class
                );

                // Wait for 5 seconds before processing the next order/payment
                Thread.sleep(5000);
            }

            // Second loop for shipping updates (using the same number of orders)
            for (int j = 0; j < orders; j++, a++) {  

                Long shippingId = (long) a;

                // Build the shipping URL with shippingId as path variable
                String shippingUrlWithParams = UriComponentsBuilder
                        .fromUriString(shippingUrl)
                        .buildAndExpand(shippingId)  
                        .toUriString();

                // Send the PATCH request to update shipping information
                ResponseEntity<Void> shippingUpdateResponse = restTemplate.exchange(
                        shippingUrlWithParams,
                        HttpMethod.PATCH,
                        null, 
                        Void.class
                );

				// Send the PATCH request to update shipping information
                ResponseEntity<Void> shippingUpdateResponse2 = restTemplate.exchange(
                        shippingUrlWithParams,
                        HttpMethod.PATCH,
                        null, 
                        Void.class
                );

                System.out.println("Shipping ID " + shippingId + " updated successfully.");

                // Wait for 3 seconds before the next shipping update
                Thread.sleep(3000);
            }
        }
    }
}
