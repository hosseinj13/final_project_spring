package com.example.homeservice.repository;

import com.example.homeservice.model.Address;
import com.example.homeservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByZipCode(String zipCode);

    boolean existsByZipCode(String zipCode);
    List<Address> findByCustomer(Customer customer);
    @Modifying
    @Query("UPDATE Address a SET a.addressLine = :street WHERE a.id = :id")
    void updateStreet(String street, Long id);
}

