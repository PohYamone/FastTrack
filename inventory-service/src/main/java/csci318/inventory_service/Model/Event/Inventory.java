package csci318.inventory_service.Model.Event;

import jakarta.persistence.Id;

public class Inventory {

    @Id
    private String productId;
    private String productName;
    private int availableStock;


    public Inventory(){

    }
    

    public Inventory(String productId, String productName, int availableStock){
        this.productId = productId;
        this.productName = productName;
        this.availableStock = availableStock;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

    public String getProductId(){
        return productId;
    }

    public void setProductName(){
        this.productName = productName;
    }
    public String getProductName(){
        return productName;
    }
    public void setAvailableQuantity(int availableStock) {
        this.availableStock = availableStock;
    }
   
}


