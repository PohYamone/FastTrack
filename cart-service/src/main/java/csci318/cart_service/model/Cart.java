package csci318.cart_service.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.AbstractAggregateRoot;

import csci318.cart_service.model.event.CartEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart extends AbstractAggregateRoot<Cart> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "creationDate")
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CartItems> items = new ArrayList<>();
    

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

    public List<CartItems> getItems() {
        return items;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    public void addItem(CartItems item) {
        this.items.add(item);

        CartEvent event = new CartEvent(CartEvent.EventType.PRODUCT_ADDED_TO_CART,this.id, item.getProductId(), null);
        registerEvent(event);

    }

    public void removeItem(CartItems item) {
        this.items.remove(item);

        CartEvent event = new CartEvent(CartEvent.EventType.PRODUCT_REMOVED_FROM_CART,this.id, item.getProductId(), null);
        registerEvent(event);
        
    }
}

