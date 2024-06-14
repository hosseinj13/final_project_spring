package com.example.homeservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "offers")

public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull(message = "Order must not be null")
    Order order;

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    @NotNull(message = "Specialist must not be null")
    Specialist specialist;

    @NotNull(message = "Proposal date must not be null")
    LocalDateTime proposalDate;

    @DecimalMin(value = "0.0", message = "Proposed price must be at least 0.0")
    double proposedPrice;

    @NotBlank(message = "Duration must not be blank")
    String duration;

}
