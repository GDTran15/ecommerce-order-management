package com.duong.ecommerce.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", unique = true ,nullable = false)
    @Size(min = 8, max = 20)
    @NotBlank
    private String username;
    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Column(name = "first_name",  nullable = false)
    private String firstName;
    @NotBlank
    @Column(name = "last_name",  nullable = false)
    private String lastName;
    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @NotBlank
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @NotBlank
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

}
