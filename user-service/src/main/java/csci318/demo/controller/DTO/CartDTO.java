package csci318.demo.controller.DTO;

import java.util.Date;

public class CartDTO {

    private Long id;
    private Long customerId;
    private Date creationDate;
    

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

}
