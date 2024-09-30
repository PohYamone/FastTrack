package csci318.demo.repository;


import csci318.demo.model.Users.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

    Customer findByEmail(String email);
    
} 

