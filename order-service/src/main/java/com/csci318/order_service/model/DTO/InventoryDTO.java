package com.csci318.order_service.model.DTO;

public class InventoryDTO {

    private Long productId;
    private String productName;
    private int availableStock;

    public InventoryDTO() {
    }

    public InventoryDTO(Long productId, String productName, int availableStock) {
        this.productId = productId;
        this.productName = productName;
        this.availableStock = availableStock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}
