package csci318.inventory_service.stream;

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

import csci318.inventory_service.Model.Event.StockEvent;


@Configuration
public class StockStreamProcessor {

    public static final String TOTAL_STOCK = "totalStock";
    private static final int LOW_STOCK_THRESHOLD = 5;
    
    @Bean
    public Consumer<KStream<Long, StockEvent>> processStock() {
        return inputStream -> {

            KTable<Long, Integer> totalStock = inputStream
                .map((key, value) -> {
                    Long productId = value.getProductId();
                    Integer stockChange = Integer.valueOf(value.getStockChange());
                    return new KeyValue<>(productId, stockChange);
                })
                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Integer())) 
                .reduce(Integer::sum, 
                        Materialized.<Long, Integer, KeyValueStore<Bytes, byte[]>>as(TOTAL_STOCK)
                        .withKeySerde(Serdes.Long()).withValueSerde(Serdes.Integer()));

            totalStock.toStream().foreach((productId, stockLevel) -> {
                if (stockLevel < LOW_STOCK_THRESHOLD) {
                    triggerLowStockAlert(productId, stockLevel);
                }
            });

            totalStock.toStream().print(Printed.<Long, Integer>toSysOut().withLabel("Current stock levels by product ID"));
        };
    }


    private void triggerLowStockAlert(Long productId, Integer stockLevel) {
        System.out.println("ALERT: Product " + productId + " has low stock: " + stockLevel);
    }
}
