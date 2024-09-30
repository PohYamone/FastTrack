package csci318.demo.model.Cart;

import java.util.Date;
import java.util.List;

public class Cart {

    private Long id;
    private Long customerId;
    private Date creationDate;
    private List<CartItem> items;

    public Date getDate(){
        return creationDate;
    }

    public void setDate(Date d){
        this.creationDate = d; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
