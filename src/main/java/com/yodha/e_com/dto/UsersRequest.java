package com.yodha.e_com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UsersRequest {
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Password is required")
    @Min(value = 6, message = "Password must be at least 6 characters long")
    private String password;
    private List<String> roles;
}
