package com.yodha.e_com.controller;

import com.yodha.e_com.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth/user")
@RestController
public class SecureUserController {

    @Autowired
    private UserServices userServices;


    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestParam String name,
                                             @RequestParam String password, Authentication authentication) {
        String currentUserEmail = authentication.getName();
        return userServices.updateUser(name, password, currentUserEmail);
    }

}
