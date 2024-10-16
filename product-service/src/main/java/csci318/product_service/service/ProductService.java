package csci318.product_service.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import csci318.product_service.repository.ProductRepository;
import csci318.product_service.controller.DTO.ProductDTO;
import csci318.product_service.model.Product;
import csci318.product_service.model.event.EventType;
import csci318.product_service.model.event.ProductEvent;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    // Constructor injection for ProductRepository.
    @Autowired
    public ProductService(ProductRepository productRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Adds a new product to the repository.
     *
     * @param p The Product object to be added.
     * @return A ResponseEntity containing the result of the product addition.
     */
    public ResponseEntity<?> addProduct(Product p) {

        // Check if the Product object is null.
        if (p == null) {
            return ResponseEntity.badRequest().body("Product data is missing");
        }

        // Check if required fields are present and valid.
        if (p.getCategory() == null || p.getName() == null || p.getPrice() < 0 || p.getWeight() < 0) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        try {

            productRepository.save(p);
            ProductEvent pe = new ProductEvent(EventType.PRODUCT_CREATED, p.getId(), p.getName());
            applicationEventPublisher.publishEvent(pe);

            return ResponseEntity.ok(p);

            // Handle any exceptions during the product addition.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add Product");
        }
    }

    /**
     * Searches for a product by its unique ID.
     *
     * @param id The UUID of the product.
     * @return A ResponseEntity containing the product or an error message.
     */
    public ProductDTO searchProduct(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            ProductDTO productDTO = new ProductDTO();

            productDTO.setProductId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());

            return productDTO;

        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }

    }

    /**
     * Retrieves all products from the repository.
     *
     * @return A ResponseEntity containing a list of all products.
     */
    public ResponseEntity<?> getProducts() {

        return ResponseEntity.ok(productRepository.findAll());

    }


    /**
 * Deletes a product by its unique ID.
 *
 * @param id The ID of the product to be deleted.
 * @return A ResponseEntity indicating the result of the delete operation.
 */
public ResponseEntity<?> deleteProduct(Long id) {
    try {
    
        Optional<Product> optionalProduct = productRepository.findById(id);

        // Check if the product exists
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Delete the product
            productRepository.delete(product);

            // Create a ProductEvent for product deletion
            ProductEvent pe = new ProductEvent(EventType.PRODUCT_DELETED, product.getId(), product.getName());
            applicationEventPublisher.publishEvent(pe);

            return ResponseEntity.ok("Product with ID " + id + " was deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Product not found with ID: " + id);
        }
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Failed to delete product with ID: " + id);
    }
}

}
