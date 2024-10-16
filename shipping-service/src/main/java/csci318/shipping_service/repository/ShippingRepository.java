package csci318.shipping_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import csci318.shipping_service.model.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long>{

    Shipping findByShippingId(Long id);

    Optional<Shipping> findByOrderId(Long orderId);

}
