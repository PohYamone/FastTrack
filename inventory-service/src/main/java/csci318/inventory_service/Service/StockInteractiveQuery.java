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

import csci318.inventory_service.Model.Event.Inventory;
import csci318.inventory_service.stream.StockStreamProcessor;

@Service
public class StockInteractiveQuery {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    public List<Inventory> getAllStockLevels() {
        List<Inventory> stockByProductList = new ArrayList<>();
        KeyValueIterator<Long, Integer> all = getTotalStockStore().all();
        while (all.hasNext()) {
            KeyValue<Long, Integer> kv = all.next();
            Inventory stockPerProduct = new Inventory();
            stockPerProduct.setProductId(kv.key);
            stockPerProduct.setAvailableStock(kv.value);
            stockByProductList.add(stockPerProduct);
        }
        return stockByProductList;
    }

    public Inventory getStockLevel(Long productId) {
        Integer stockLevel = getTotalStockStore().get(productId);

        if (stockLevel == null) {
            throw new RuntimeException("Product with ID " + productId + " not found in stock.");
        }

        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setAvailableStock(stockLevel);
        return inventory;
    }

    private ReadOnlyKeyValueStore<Long, Integer> getTotalStockStore() {
        return this.interactiveQueryService.getQueryableStore(StockStreamProcessor.TOTAL_STOCK,
                QueryableStoreTypes.keyValueStore());
    }
}
