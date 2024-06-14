package com.example.homeservice.model;


import com.example.homeservice.enums.SpecialistStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "specialists")
public class Specialist extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Specialist status must not be null")
    @Enumerated(EnumType.STRING)
    SpecialistStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "specialist_subservice",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
     Set<SubService> subServices = new HashSet<>();

    @Min(value = 0, message = "Years of experience must be at least 0")
    int yearsOfExperience;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
    double rating;

    @Lob
   byte[] profilePicture;
    
}
