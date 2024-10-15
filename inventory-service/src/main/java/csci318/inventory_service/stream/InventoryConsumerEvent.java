package csci318.inventory_service.stream;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import csci318.inventory_service.Model.DTO.InventoryDTO;
import csci318.inventory_service.Model.Event.ProductEvent;
import csci318.inventory_service.Service.InventoryService;

@Service
public class InventoryConsumerEvent {

    private final InventoryService inventoryService;

    public InventoryConsumerEvent(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    @Bean
    public Consumer<ProductEvent> process() {
        return productEvent -> {

            System.out.println("Name:" + productEvent.getName());
            InventoryDTO idt = new InventoryDTO(productEvent.getId(), productEvent.getName(), 0);
            inventoryService.addProduct(idt);

        };
    }

}
