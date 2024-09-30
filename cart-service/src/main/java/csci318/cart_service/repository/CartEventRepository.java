package csci318.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import csci318.cart_service.model.event.CartEvent;

public interface CartEventRepository extends JpaRepository<CartEvent,Long>{
    
}
