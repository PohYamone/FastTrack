

package csci318.inventory_service.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import csci318.inventory_service.Model.Event.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    
    // Custom query method to find products by name
    List<Inventory> findByProductName(String productName);
}

