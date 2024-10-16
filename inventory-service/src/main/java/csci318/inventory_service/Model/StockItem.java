package csci318.inventory_service.Model;

import java.io.Serializable;

public class StockItem implements Serializable{

    private Long productId;
    private int stock;

    public StockItem() {}

    public StockItem(Long productId, int stock) {
        this.productId = productId;
        this.stock = stock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

