package csci318.inventory_service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import csci318.inventory_service.InventoryServiceApplication;
import csci318.inventory_service.Model.Event.Inventory;
import csci318.inventory_service.Service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Create a new product in the inventory
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.addProduct(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
    }

    // Get a product by its product ID
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        Inventory inventory = inventoryService.getProductById(productId);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    // Get all products in the inventory
    @GetMapping("/products")
    public List<Inventory> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    // Update stock for a specific product
    @PutMapping("/products/{productId}/stock")
    public ResponseEntity<?> updateStock(@PathVariable String productId, @RequestParam int amount) {
        boolean updated = inventoryService.updateStock(productId, amount);
        if (updated) {
            return ResponseEntity.ok("Stock updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    // Remove a product from the inventory
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable String productId) {
        boolean removed = inventoryService.removeProduct(productId);
        if (removed) {
            return ResponseEntity.ok("Product removed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
