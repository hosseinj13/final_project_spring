package com.example.homeservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "sub_service")

public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Name must not be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    String subserviceName;

    @Min(value = 0, message = "Price must be at least 0")
    double price;

    @Size(max = 500, message = "Description must be at most 500 characters")
    String description;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    @NotNull(message = "Service must not be null")
    MainService service;


    @ManyToMany(mappedBy = "subServices")
    private Set<Specialist> specialists = new HashSet<>();

}
