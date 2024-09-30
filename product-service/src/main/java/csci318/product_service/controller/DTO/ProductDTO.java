package csci318.product_service.controller.DTO;

public class ProductDTO {
    
    private Long productId;
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
}
