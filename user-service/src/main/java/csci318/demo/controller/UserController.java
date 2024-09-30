package csci318.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csci318.demo.model.Cart.Cart;
import csci318.demo.model.Users.Customer;
import csci318.demo.service.UserService;
import csci318.demo.controller.DTO.CartDTO;
import csci318.demo.controller.DTO.CartItemDTO;
import csci318.demo.controller.Requests.ProductRequest;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer){
        return userService.registerCustomer(customer);
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers(){
        return userService.getCustomers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Customer user){
        return userService.loginCustomer(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id){
        return userService.getCustomer(id);
    }

    @PostMapping("/{customerId}/carts")
     public CartDTO createCart(@PathVariable Long customerId) {
        return userService.createCartForCustomer(customerId);
    }

    @GetMapping("/{customerId}/carts")
     public List<CartDTO> getCarts(@PathVariable Long customerId) {
        return userService.getCartsForCustomer(customerId);
    }

    @GetMapping("/{customerId}/carts/{cartId}")
     public Cart getCart(@PathVariable Long customerId, @PathVariable Long cartId) {
        return userService.getCartByIdForCustomer(customerId, cartId);
    }

    @GetMapping("/{customerId}/carts/{cartid}/products")
     public List<CartItemDTO> getProductsFromCart(@PathVariable Long customerId, @PathVariable Long cartid) {
        return userService.getProductsFromCart(cartid);
    }

    @PutMapping("/{customerId}/carts/{cartid}/products")
     public ResponseEntity<?> addProductToCart(@PathVariable Long customerId, @PathVariable Long cartid, @RequestBody ProductRequest productRequest) {
        return userService.addProductToCart(cartid, productRequest);
    }

    @DeleteMapping("/{customerId}/carts/{cartid}/products/{productId}")
     public ResponseEntity<?> removeProductFromCart(@PathVariable Long customerId, @PathVariable Long cartid, @PathVariable Long productId) {
        return userService.removeProductFromCart(cartid, productId);
    }

    
}
