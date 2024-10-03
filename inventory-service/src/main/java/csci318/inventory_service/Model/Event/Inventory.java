package csci318.inventory_service.Model.Event;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "available_stock")
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



    public void setProductName(String productName){
        this.productName = productName;
    }
    public String getProductName(){
        return productName;
    }



    public void setAvailableQuantity(int availableStock) {
        this.availableStock = availableStock;
    }
    public int getAvailableQuantity(int availableStock) {
        return availableStock;
    }
   
}



