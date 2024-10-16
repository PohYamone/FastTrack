package csci318.analytics_service.controller;

import csci318.analytics_service.stream.StockInteractiveQuery;
import csci318.analytics_service.model.StockItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockInteractiveQuery stockInteractiveQuery;

    // Endpoint to get all stock levels
    @GetMapping("/levels")
    public List<StockItem> getAllStockLevels() {
        return stockInteractiveQuery.getAllStockLevels();
    }

    // Endpoint to get stock level for a specific product
    @GetMapping("/levels/{productId}")
    public StockItem getStockLevel(@PathVariable Long productId) {
        return stockInteractiveQuery.getStockLevel(productId);
    }
}
