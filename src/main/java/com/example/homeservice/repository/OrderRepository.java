package com.example.homeservice.repository;

import com.example.homeservice.enums.OrderStatus;
import com.example.homeservice.model.Customer;
import com.example.homeservice.model.Order;
import com.example.homeservice.model.Specialist;
import com.example.homeservice.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
   List<Order> findAllById(Long id);
   List<Order> findAllByCustomerAndStatusIn(Customer customer, List<OrderStatus> orderStatus);
   List<Order> findAllBySubServiceAndStatus(SubService subService, OrderStatus status);
   List<Order> findBySelectedSpecialist(Specialist specialist);
    @Modifying
    @Query("UPDATE Order o SET o.address = :address WHERE o.id = :id")
    void updateOrdersByAddress(String address, Long id);

}
