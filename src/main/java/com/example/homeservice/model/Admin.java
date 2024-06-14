package com.example.homeservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
@Entity

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    String username;

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    public Admin(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
