package com.example.homeservice.model;


import com.example.homeservice.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Description must not be blank")
    String description;

    @NotNull(message = "createdAt date must not be null")
    LocalDateTime createdAt;

    @DecimalMin(value = "0.0", message = "Final price must be at least 0.0")
    double finalPrice;

    @NotNull(message = "Order status must not be null")
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Offer> offers;

    @ManyToOne
    @JoinColumn(name = "sub_Service_id", nullable = false)
    SubService subService;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    Specialist selectedSpecialist;

    @ManyToOne
    Address address;
}

