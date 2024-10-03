package csci318.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import csci318.demo.controller.DTO.CartDTO;
import csci318.demo.controller.DTO.CartItemDTO;
import csci318.demo.controller.Requests.ProductRequest;
import csci318.demo.model.Cart.Cart;
import csci318.demo.model.Users.Customer;
import csci318.demo.model.event.UserEvent;
import csci318.demo.repository.CustomerRepository;


@Service
public class UserService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${cart.service.url}")
    private String CART_URL;

    // Constructor injection for CustomerRepository.
    @Autowired
    public UserService(CustomerRepository customerRepository, RestTemplate restTemplate, ApplicationEventPublisher applicationEventPublisher){
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

     /**
      * Registers a new customer.
      * 
      * @param c The Customer object to be registered.
      * @return A ResponseEntity containing the result of the registration process.
      */

    public ResponseEntity<?> registerCustomer(Customer c) {

        // Check if the Customer object is null.
        if (c == null) {
            return ResponseEntity.badRequest().body("Customer data is missing");
        }

        // Check if required fields are present.
        if (c.getName() == null || c.getEmail() == null || c.getPassword() == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        try {
            
            customerRepository.save(c);

            UserEvent registerEvent = new UserEvent(UserEvent.EventType.USER_REGISTERED, null, null, c.getId());
            applicationEventPublisher.publishEvent(registerEvent);

            return ResponseEntity.ok(c);
        } 
        catch (Exception e) {
            // Handle any exceptions during registration.
            return ResponseEntity.badRequest().body("Failed to register: " + e.getMessage());
        }
    }


    /**
     * Authenticates a customer based on their email and password.
     * 
     * @param u The Customer object containing email and password credentials.
     * @return A ResponseEntity containing the authenticated customer or an error message.
     */
    public ResponseEntity<?> loginCustomer(Customer u) {

        Customer customer = customerRepository.findByEmail(u.getEmail());

        // Check if the customer exists.
        if (customer != null){
            if(u.getPassword().equals(customer.getPassword())){

                UserEvent loginEvent = new UserEvent(UserEvent.EventType.USER_LOGGED_IN, null, null, customer.getId());
                applicationEventPublisher.publishEvent(loginEvent);

                return ResponseEntity.ok(customer);
            }

            // Return an error if the passwords don't match.
            return ResponseEntity.status(999).body("Passwords don't match");
        }

        // Return an error if the user is not found.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");

    }


    /**
     * Retrieves a customer by their unique ID.
     * 
     * @param id The UUID of the customer.
     * @return A ResponseEntity containing the customer or an error message.
     */
    public ResponseEntity<?> getCustomer(Long id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
    
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }


    /**
     * Retrieves all customers.
     * 
     * @return A ResponseEntity containing a list of all customers.
     */
    public ResponseEntity<?> getCustomers() {

        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);

    }

    
    
    /**
     * Creates a new cart for a specific customer.
     * 
     * @param customerId The ID of the customer for whom the cart will be created.
     * @return The newly created CartDTO object.
     */
    public CartDTO createCartForCustomer(Long customerId) {

        String url = CART_URL + "/user/" + customerId;
        
        CartDTO createdCart = restTemplate.postForObject(url, null, CartDTO.class);

        UserEvent cartCreatedEvent = new UserEvent(UserEvent.EventType.CART_CREATED,createdCart.getId() ,null , customerId);
        applicationEventPublisher.publishEvent(cartCreatedEvent);

        return createdCart;
    }



    /**
     * Retrieves a cart for a specific customer.
     * 
     * @param customerId The ID of the customer whose cart is being retrieved.
     * @return The CartDTO object containing cart details.
     */
    public List<CartDTO> getCartsForCustomer(Long customerId) {

        String url = CART_URL + "/user/" + customerId;
        CartDTO[] cartArray = restTemplate.getForObject(url, CartDTO[].class);

        return Arrays.asList(cartArray);
    }



    /**
     * Adds a product to a specific cart.
     * 
     * @param cartId The ID of the cart to which the product will be added.
     * @param productRequest The ProductRequest object containing product details.
     * @return ResponseEntity<?> containing the result of the operation.
     */
    public ResponseEntity<?> addProductToCart(Long cartId, ProductRequest productRequest) {
        String url = CART_URL + cartId + "/products";

        try {
            restTemplate.put(url, productRequest);

            UserEvent productAddedEvent = new UserEvent(UserEvent.EventType.PRODUCT_ADDED_TO_CART, cartId, productRequest.getProductId(), null);
            applicationEventPublisher.publishEvent(productAddedEvent);

            return ResponseEntity.ok("Product added successfully to the cart.");
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error occurred while adding the product to the cart: " + e.getMessage());
        }
        
    }



    /**
     * Retrieves a specific cart for a customer by cart ID.
     * 
     * @param customerId The ID of the customer.
     * @param cartId The ID of the cart to be retrieved.
     * @return The Cart object containing cart details.
     */
    public Cart getCartByIdForCustomer(Long customerId, Long cartId){

        String url = CART_URL + cartId;
        return restTemplate.getForObject(url, Cart.class);

    }




    /**
     * Removes a specific product from a cart.
     * 
     * @param cartId The ID of the cart from which the product will be removed.
     * @param productId The ID of the product to be removed.
     */
    public ResponseEntity<?> removeProductFromCart(Long cartId, Long productId){

        String url = CART_URL + cartId + "/products/" + productId;

        try {
            restTemplate.delete(url);

            UserEvent productAddedEvent = new UserEvent(UserEvent.EventType.PRODUCT_REMOVED_FROM_CART, cartId, productId, null);
        applicationEventPublisher.publishEvent(productAddedEvent);

            return ResponseEntity.ok("Product removed successfully from the cart.");
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error occurred while remove the product from the cart: " + e.getMessage());
        }

    }




    /**
     * Retrieves all products from a specific cart.
     * 
     * @param cartId The ID of the cart from which products will be retrieved.
     * @return A list of CartItemDTO objects representing the products in the cart.
     */
    public List<CartItemDTO> getProductsFromCart(Long cartid){

        String url = CART_URL + cartid + "/products";
        CartItemDTO[] cartItemsArray = restTemplate.getForObject(url, CartItemDTO[].class);

        return Arrays.asList(cartItemsArray);
    }


}
