package com.yodha.e_com.controller;


import com.yodha.e_com.dto.LoginRequest;
import com.yodha.e_com.dto.UsersRequest;
import com.yodha.e_com.dto.UsersResponsedto;
import com.yodha.e_com.services.UserServices;
import com.yodha.e_com.utils.ApiResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<UsersResponsedto>> register(@Valid @RequestBody UsersRequest request) {
        UsersResponsedto newUser = userServices.createUser(request);
        return ResponseEntity.ok(new ApiResponse<>(true,
                "Account created successfully!",
                newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UsersResponsedto>> login(@RequestBody LoginRequest user) {
        return userServices.loginUser(user.getEmail(), user.getPassword());
    }

}
