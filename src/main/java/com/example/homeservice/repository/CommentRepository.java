package com.example.homeservice.repository;

import com.example.homeservice.model.Customer;
import com.example.homeservice.model.Order;
import com.example.homeservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByOrder(Order order);

    List<Comment> findAllByCustomer(Customer customer);

    @Modifying
    @Query("UPDATE Comment c SET c.comment = :comment WHERE c.id = :id")
    void updateReviewByComment(String comment, Long id);

    List<Comment> findByCustomer(Customer customer);
}
