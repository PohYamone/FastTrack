package csci318.inventory_service.Model.Event;

import java.io.Serializable;

public class StockEvent implements Serializable{
    private Long productId;
    private int stockChange;

    public StockEvent() {}

    public StockEvent(Long productId, int stockChange) {
        this.productId = productId;
        this.stockChange = stockChange;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getStockChange() {
        return stockChange;
    }

    public void setStockChange(int stockChange) {
        this.stockChange = stockChange;
    }
}

