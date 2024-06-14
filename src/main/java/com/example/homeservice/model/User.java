package com.example.homeservice.model;

import com.example.homeservice.base.entity.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@SuperBuilder
@MappedSuperclass

public class User extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "First name must not be null")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    String firstName;

    @NotNull(message = "Last name must not be null")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    String lastName;

    @NotNull(message = "Phone number must not be null")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be valid")
    String phoneNumber;

    @NotNull(message = "Email must not be null")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    String email;

    @NotNull(message = "Username must not be null")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 8, max = 10, message = "Password must be at least 8 characters long")
    String password;

    @Min(value = 0, message = "Credit must be at least 0")
    double credit;

//    @NotNull(message = "Role must not be null")
//    @Enumerated(EnumType.STRING)
//    Role role;
}
