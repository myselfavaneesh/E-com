package com.yodha.e_com.services;

import com.yodha.e_com.dto.UsersRequest;
import com.yodha.e_com.dto.UsersResponsedto;
import com.yodha.e_com.entities.Users;
import com.yodha.e_com.exception.ResourceNotFoundException;
import com.yodha.e_com.mapper.UserMapper;
import com.yodha.e_com.repository.UserRepo;
import com.yodha.e_com.security.AppUserDetails;
import com.yodha.e_com.utils.ApiResponse;
import com.yodha.e_com.utils.JWTUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class UserServices {

    private final AuthenticationManager authManager;
    private final UserRepo userRepo;
    private final JWTUtility jwtUtility;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper usersMapper;

    public UserServices(AuthenticationManager authManager, UserRepo userRepo, JWTUtility jwtUtility, PasswordEncoder passwordEncoder, UserMapper usersMapper) {
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
        this.usersMapper = usersMapper;
    }

    public UsersResponsedto createUser(UsersRequest request) {
        userRepo.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new ResourceNotFoundException("Email is already in use!");
        });

        Users user = usersMapper.toUsers(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        return usersMapper.toUsersResDto(user);
    }

    public ResponseEntity<ApiResponse<UsersResponsedto>> loginUser(String email, String password) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

            String jwtToken = jwtUtility.generateToken(userDetails.getUsername(), userDetails.getRoles());


            ResponseCookie jwtCookie = ResponseCookie.from("JWT-TOKEN", jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new ApiResponse<>(true, "Login successful", usersMapper.toUsersResDto(userDetails.getUser())));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid email or password", null));
        }
    }

    public UsersResponsedto updateUser(String name, String password, String currentUserEmail) {
        Users user = userRepo.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (name != null) {
            user.setName(name);
        }

        if (!password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepo.save(user);
        return usersMapper.toUsersResDto(user);
    }

    public UsersResponsedto getUserByEmail(String email) {
        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return usersMapper.toUsersResDto(user);
    }

}


