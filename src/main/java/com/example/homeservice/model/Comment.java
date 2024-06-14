package com.example.homeservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "comments")

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull(message = "Order must not be null")
    Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer must not be null")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    @NotNull(message = "Specialist must not be null")
    Specialist specialist;


    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating;

    @Size(max = 1000, message = "Comment must be at most 1000 characters")
    String comment;

    @PastOrPresent(message = "Review date must be in the past or present")
    LocalDateTime commentDate;


}

