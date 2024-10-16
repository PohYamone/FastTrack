package csci318.analytics_service.stream;

import java.util.function.Consumer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import csci318.analytics_service.model.event.OrderEvent;
import csci318.analytics_service.model.event.OrderEventType;

@Configuration
public class UserOrderStreamProcessor {

    public static final String ACTIVE_ORDERS = "active_Orders";

    @Bean
    public Consumer<KStream<Long, OrderEvent>> processOrders() {
        return inputStream -> {

            KTable<Long, Long> activeOrderCount = inputStream
                .filter((key, value) -> value.getEventType() == OrderEventType.ORDER_CREATED || 
                                        value.getEventType() == OrderEventType.ORDER_SHIPPED || 
                                        value.getEventType() == OrderEventType.ORDER_CONFIRMED) 
                .map((key, value) -> {
                    Long userId = value.getCustomerId(); 
                    Long countChange = (value.getEventType() == OrderEventType.ORDER_CREATED) ? 1L : -1L; // Increment or decrement based on event type
                    return new KeyValue<>(userId, countChange);
                })
                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long())) // Group by userId
                .reduce(Long::sum, // Aggregate to get the count of active orders
                        Materialized.<Long, Long, KeyValueStore<Bytes, byte[]>>as(ACTIVE_ORDERS)
                        .withKeySerde(Serdes.Long()).withValueSerde(Serdes.Long()));

            // Output the current active order counts
            activeOrderCount.toStream().foreach((userId, orderCount) -> {
                System.out.println("User ID: " + userId + " has " + orderCount + " active orders.");
            });

            activeOrderCount.toStream().print(Printed.<Long, Long>toSysOut().withLabel("Active Orders Count"));
        };
    }
}
