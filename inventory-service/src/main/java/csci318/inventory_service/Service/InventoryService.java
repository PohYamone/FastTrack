package csci318.inventory_service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import csci318.inventory_service.Model.DTO.InventoryDTO;
import csci318.inventory_service.Model.Event.Inventory;
import csci318.inventory_service.Model.Event.StockEvent;
import csci318.inventory_service.Repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    private KafkaTemplate<String, StockEvent> kafkaTemplate;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // Convert Inventory entity to InventoryDTO
    private InventoryDTO convertToDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getProductId(),
                inventory.getProductName(),
                inventory.getAvailableStock());
    }

    // Convert InventoryDTO to Inventory entity
    private Inventory convertToEntity(InventoryDTO inventoryDTO) {
        return new Inventory(
                inventoryDTO.getProductId(),
                inventoryDTO.getProductName(),
                inventoryDTO.getAvailableStock());
    }

    public InventoryDTO addProduct(InventoryDTO inventoryDTO) {
        Inventory inventory = convertToEntity(inventoryDTO);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return convertToDTO(savedInventory);
    }

    public InventoryDTO getProductById(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return convertToDTO(inventory);
    }

    public List<InventoryDTO> getAllProducts() {
        return inventoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean updateStock(Long productId, int quantityChange) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        int newStock = inventory.getAvailableStock() + quantityChange;

        if (newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock for product with id: " + productId);
        }

        inventory.setAvailableStock(newStock);
        inventoryRepository.save(inventory);

        StockEvent event = new StockEvent(productId, quantityChange);
        kafkaTemplate.send("stock-events-topic", event);
        return true;
    }

    public boolean removeProduct(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        inventoryRepository.delete(inventory);
        return true;
    }

    public boolean isProductInStock(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return inventory.getAvailableStock() > 0;
    }
}
