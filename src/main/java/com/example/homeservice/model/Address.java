package com.example.homeservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "addresses")

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "City must not be blank")
    String city;

    @NotBlank(message = "State must not be blank")
    String state;

    @NotBlank(message = "Street must not be blank")
    String addressLine;


    @NotBlank(message = "Zip code must not be blank")
    @Pattern(regexp = "\\d{10}", message = "Zip code must be a 10 digit number")
    String zipCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
     Set<Order> orders;
}
