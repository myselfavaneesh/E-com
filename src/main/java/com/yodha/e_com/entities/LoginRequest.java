package com.yodha.e_com.entities;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

