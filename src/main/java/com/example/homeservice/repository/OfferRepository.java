package com.example.homeservice.repository;

import com.example.homeservice.model.Order;
import com.example.homeservice.model.Offer;
import com.example.homeservice.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByOrderId(Long orderId);
    Optional<Offer> findBySpecialist(Specialist specialist);
    Optional<Offer> findByOrder(Order order);

    @Modifying
    @Query("UPDATE Offer p SET p.proposedPrice = :proposedPrice WHERE p.id = :id")
    void updateProposalByProposedPrice(double proposedPrice, int id);
}
