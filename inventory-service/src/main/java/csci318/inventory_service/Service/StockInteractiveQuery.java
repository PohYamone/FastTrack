package csci318.inventory_service.Service;

import java.util.List;
import java.util.ArrayList;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import csci318.inventory_service.Model.StockItem;
import csci318.inventory_service.stream.StockStreamProcessor;






@Service
public class StockInteractiveQuery {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    public List<StockItem> getAllStockLevels() {
        List<StockItem> stockByProductList = new ArrayList<>();
        KeyValueIterator<Long, Integer> all = getTotalStockStore().all();
        while (all.hasNext()) {
            KeyValue<Long, Integer> kv = all.next();
            StockItem stockPerProduct = new StockItem();
            stockPerProduct.setProductId(kv.key);
            stockPerProduct.setStock(kv.value);
            stockByProductList.add(stockPerProduct);
        }
        return stockByProductList;
    }

    public StockItem getStockLevel(Long productId) {
        Integer stockLevel = getTotalStockStore().get(productId);

        if (stockLevel == null) {
            throw new RuntimeException("Product with ID " + productId + " not found in stock.");
        }

        StockItem inventory = new StockItem();
        inventory.setProductId(productId);
        inventory.setStock(stockLevel);
        return inventory;
    }

    private ReadOnlyKeyValueStore<Long, Integer> getTotalStockStore() {
        return this.interactiveQueryService.getQueryableStore(StockStreamProcessor.TOTAL_STOCK,
                QueryableStoreTypes.keyValueStore());
    }

    
}
