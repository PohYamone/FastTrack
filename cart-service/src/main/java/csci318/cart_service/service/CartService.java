package csci318.cart_service.service;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import csci318.cart_service.controller.DTO.CartDTO;
import csci318.cart_service.controller.DTO.CartItemDTO;
import csci318.cart_service.model.Cart;
import csci318.cart_service.model.CartItems;
import csci318.cart_service.model.event.CartEvent;
import csci318.cart_service.model.event.UserEvent;
import csci318.cart_service.repository.CartRepository;
import csci318.cart_service.controller.DTO.ProductDTO;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CartService(CartRepository cartRepository, RestTemplate restTemplate,
            ApplicationEventPublisher applicationEventPublisher) {
        this.cartRepository = cartRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    public Consumer<UserEvent> userRegisterConsumer() {
        return userEvent -> {
            System.out.println("Received UserRegistered event for user ID: " + userEvent.getCustomerId());
            createCartForCustomer(userEvent.getCustomerId());
        };
    }

    /**
     * Creates a new cart for a specific customer.
     * 
     * @param customerId The ID of the customer for whom the cart will be created.
     * @return The newly created CartDTO object.
     */
    public CartDTO createCartForCustomer(Long customerId) {

        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        cart.setItems(new ArrayList<>());

        Date date = new Date();
        cart.setDate(date);

        cartRepository.save(cart);

        CartEvent createCartEvent = new CartEvent(CartEvent.EventType.CART_CREATED, cart.getId(), null, customerId);
        applicationEventPublisher.publishEvent(createCartEvent);

        CartDTO cd = new CartDTO();
        cd.setCustomerId(cart.getCustomerId());
        cd.setId(cart.getId());
        cd.setDate(date);

        return cd;

    }

    /**
     * Retrieves all carts associated with a specific customer by their customer ID.
     * 
     * @param customerId The ID of the customer whose carts are being retrieved.
     * @return A list of CartDTO objects containing cart details.
     */
    public List<CartDTO> getCartsByCustomerId(Long customerId) {

        List<Cart> carts = cartRepository.findByCustomerId(customerId);

        if (carts.isEmpty()) {
            throw new RuntimeException("No carts found for customer with customer id: " + customerId);
        }

        List<CartDTO> cartDTOs = new ArrayList<>();

        for (Cart cart : carts) {
            CartDTO csDto = new CartDTO();
            csDto.setCustomerId(cart.getCustomerId());
            csDto.setId(cart.getId());
            csDto.setDate(cart.getDate());

            cartDTOs.add(csDto);
        }

        return cartDTOs;

    }

    /**
     * Retrieves a specific cart by its cart ID.
     * 
     * @param cartId The ID of the cart to be retrieved.
     * @return The CartDTO object containing cart details.
     * @throws RuntimeException if the cart is not found.
     */
    public CartDTO getCartByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        // Convert the Cart entity to CartDTO
        return convertToCartDTO(cart);
    }

    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCustomerId(cart.getCustomerId());
        cartDTO.setDate(cart.getDate());

        // Convert CartItems to CartItemDTO
        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());

        cartDTO.setItems(cartItemDTOs);
        return cartDTO;
    }

    private CartItemDTO convertToCartItemDTO(CartItems cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setName(cartItem.getName()); // Assuming CartItems contains the product name
        return cartItemDTO;
    }

    /**
     * Checks if a product exists by querying the product service.
     * 
     * @param productId The ID of the product to check.
     * @return The ProductDTO object if the product exists.
     */
    public ProductDTO checkProduct(Long productId) {

        String PRODUCT_URL = "http://localhost:8081/api/products/";
        String url = PRODUCT_URL + productId;

        ProductDTO pdt = restTemplate.getForObject(url, ProductDTO.class);

        return pdt;

    }

    /**
     * Adds a product to a specific cart.
     * 
     * @param cartId    The ID of the cart to which the product will be added.
     * @param cartItems The CartItems object containing the product details and
     *                  quantity.
     * @return The updated Cart object.
     */
    public boolean addProductToCart(Long cartId, CartItems cartItems) {

        ProductDTO pdt = checkProduct(cartItems.getProductId());
        if (pdt == null) {
            return false;
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        cartItems.setName(pdt.getName());
        cart.addItem(cartItems);

        cartRepository.save(cart);
        return true;
    }

    /**
     * Removes a specific product from a cart.
     * 
     * @param cartId    The ID of the cart from which the product will be removed.
     * @param productId The ID of the product to be removed.
     * @return true if the product was removed successfully, false otherwise.
     */
    public boolean removeProduct(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart == null) {
            return false;
        }

        List<CartItems> items = cart.getItems();
        if (items == null || items.isEmpty()) {
            return false;
        }

        CartItems productToRemove = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (productToRemove != null) {

            cart.removeItem(productToRemove);
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    /**
     * Retrieves all products from a specific cart.
     * 
     * @param cartId The ID of the cart from which products will be retrieved.
     * @return A list of CartItemDTO objects representing the products in the cart.
     */
    public List<CartItemDTO> getProductsFromCart(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();

        for (CartItems c : cart.getItems()) {

            CartItemDTO cI = new CartItemDTO();

            cI.setId(c.getId());
            cI.setProductId(c.getProductId());
            cI.setQuantity(c.getQuantity());
            cI.setName(c.getName());

            cartItemDTOs.add(cI);

        }

        return cartItemDTOs;
    }

}
