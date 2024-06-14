package com.example.homeservice.repository;

import com.example.homeservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    Customer findByUsernameAndPassword(String username, String password);
    @Modifying
    @Query("UPDATE Customer u SET u.username = :username WHERE u.id = :id")
    void updateUsername(String username, Long id);


}
