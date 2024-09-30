package csci318.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import csci318.demo.model.event.UserEvent;

public interface CustomerEventRepository extends JpaRepository<UserEvent,Long>{
    
}
