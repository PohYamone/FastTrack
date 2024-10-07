package csci318.shipping_service.model.dto;

import csci318.shipping_service.model.Address;

public class Customer {

    private Long id;
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
}
