package csci318.analytics_service.stream;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import csci318.analytics_service.model.UserOrder;

@Service
public class OrderInteractiveQuery {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    public List<UserOrder> getAllActiveOrders() {
        List<UserOrder> activeOrdersList = new ArrayList<>();
        KeyValueIterator<Long, Long> all = getActiveOrderStore().all();
        while (all.hasNext()) {
            KeyValue<Long, Long> kv = all.next();
            UserOrder userOrder = new UserOrder();
            userOrder.setUserId(kv.key);
            userOrder.setActiveOrderCount(kv.value);
            activeOrdersList.add(userOrder);
        }
        return activeOrdersList;
    }

    public UserOrder getActiveOrderCount(Long userId) {
        Long activeOrderCount = getActiveOrderStore().get(userId);

        if (activeOrderCount == null) {
            throw new RuntimeException("User with ID " + userId + " has no active orders.");
        }

        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(userId);
        userOrder.setActiveOrderCount(activeOrderCount);
        return userOrder;
    }

    private ReadOnlyKeyValueStore<Long, Long> getActiveOrderStore() {
        return this.interactiveQueryService.getQueryableStore(UserOrderStreamProcessor.ACTIVE_ORDERS,
                QueryableStoreTypes.keyValueStore());
    }
}
