package csci318.product_service.model.event;


public class ProductEvent {

      
    private Long pid;
    private String name;
    private EventType eventType;

    public enum EventType {
        PRODUCT_CREATED,
        PRODUCT_DELETED
    }

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
