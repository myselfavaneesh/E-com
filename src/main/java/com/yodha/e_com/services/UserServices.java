package com.yodha.e_com.services;

import com.yodha.e_com.entities.Users;
import com.yodha.e_com.repository.UserRepo;
import com.yodha.e_com.security.AppUserDetails;
import com.yodha.e_com.utils.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserServices {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public ResponseEntity<String> createUser(Users user){
        try{
            if (userRepo.existsByEmail(user.getEmail())) {
                return ResponseEntity.badRequest()
                        .body("Error: Email is already taken!");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return ResponseEntity.ok("User registered successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest()
                        .body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<?> loginUser(String email, String password) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

            String jwtToken = jwtUtility.generateToken(userDetails.getUsername(),userDetails.getRoles());



            ResponseCookie jwtCookie = ResponseCookie.from("JWT-TOKEN", jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body("Login successful");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }

    public ResponseEntity<String> updateUser(String name ,String password, String currentUserEmail ){
        try {
            Users user = userRepo.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (name != null) {
                user.setName(name);
            }


            //Future me Aur Secure Karna hai
            if (!password.isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
            }
            userRepo.save(user);
            return ResponseEntity.ok("User updated successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error updating user: " + e.getMessage());
        }
    }

}


