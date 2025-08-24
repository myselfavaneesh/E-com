package com.yodha.e_com.controller;


import com.yodha.e_com.entities.LoginRequest;
import com.yodha.e_com.entities.Users;
import com.yodha.e_com.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth/user")
@RestController
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody Users user){
        return userServices.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user){
        return userServices.loginUser(user.getEmail(), user.getPassword());
    }

}
