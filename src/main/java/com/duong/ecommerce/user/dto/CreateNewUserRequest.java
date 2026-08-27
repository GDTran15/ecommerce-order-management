package com.duong.ecommerce.user.dto;

import com.duong.ecommerce.common.validation.OnCreate;
import com.duong.ecommerce.common.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;

public record CreateNewUserRequest (
        @NotBlank(message = "Username is required",groups = OnCreate.class)
        @Size(min = 8, max = 20, message = "Username must be between 8 and 20 characters", groups = {OnCreate.class, OnUpdate.class})
        String username,
        @NotBlank(message = "Password is required", groups = OnCreate.class)
        String password,
        @NotBlank(message = "First name is required" ,groups = OnCreate.class)
        String firstName,
        @NotBlank(message = "Last name is required" ,groups = OnCreate.class)
        String lastName,
        @NotBlank(message = "Email is required",groups = OnCreate.class)
        @Email(message = "Email is invalid", groups = {OnCreate.class, OnUpdate.class})
        String email,
        @NotBlank(message = "Phone number is required", groups = OnCreate.class)
        String phoneNumber,
        @NotNull(message = "Date of birth is required" ,groups = OnCreate.class)
        LocalDate dateOfBirth

){}
