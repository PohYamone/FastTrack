
package csci318.inventory_service.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import csci318.inventory_service.Model.Event.Inventory;
import csci318.inventory_service.Repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param inventory The Inventory object containing product details.
     * @return The created Inventory object.
     */
    public Inventory addProduct(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    /**
     * Retrieves a product from the inventory by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The Inventory object, or null if the product is not found.
     */
    public Inventory getProductById(String productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    /**
     * Retrieves all products in the inventory.
     *
     * @return A list of Inventory objects representing all products.
     */
    public List<Inventory> getAllProducts() {
        return inventoryRepository.findAll();
    }

    /**
     * Updates the stock level of a product in the inventory.
     *
     * @param productId The ID of the product to update.
     * @param newStock The new stock level.
     * @return true if the stock was successfully updated, false otherwise.
     */
    public boolean updateStock(String productId, int newStock) {
        Inventory inventory = getProductById(productId);

        if (inventory != null && newStock >= 0) {
            inventory.setAvailableStock(newStock);
            inventoryRepository.save(inventory);
            return true;
        }

        return false;
    }

    /**
     * Removes a product from the inventory by its ID.
     *
     * @param productId The ID of the product to remove.
     * @return true if the product was successfully removed, false otherwise.
     */
    public boolean removeProduct(String productId) {
        Inventory inventory = getProductById(productId);
        
        if (inventory != null) {
            inventoryRepository.delete(inventory);
            return true;
        }
        
        return false;
    }

    /**
     * Checks if a product is in stock.
     *
     * @param productId The ID of the product to check.
     * @return true if the product is in stock, false otherwise.
     */
    public boolean isProductInStock(String productId) {
        Inventory inventory = getProductById(productId);
        return inventory != null && inventory.getAvailableStock() > 0;
    }

  
}
