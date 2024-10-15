package csci318.inventory_service.Model.Event;

import java.io.Serializable;

public class ProductEvent implements Serializable{

    private Long pid;
    private String name;
    private EventType eventType;

    public ProductEvent(){};

    public ProductEvent(EventType eventType, Long productId, String name) {
        this.eventType = eventType;
        this.name = name;
        this.pid = productId;
    }

    public Long getId() {
        return this.pid;
    }

    public void setId(Long id) {
        this.pid = id;
    }

    public EventType getType(){
        return this.eventType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
