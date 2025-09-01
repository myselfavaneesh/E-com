package com.yodha.e_com.controller;

import com.yodha.e_com.dto.UsersResponsedto;
import com.yodha.e_com.services.UserServices;
import com.yodha.e_com.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ApiResponse<UsersResponsedto>> updateUser(@RequestParam(required = false) String name,
                                                                    @RequestParam(required = false) String password) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", userServices.updateUser(name, password, email)));
    }

}
