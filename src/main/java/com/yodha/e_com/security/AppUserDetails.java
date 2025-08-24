package com.yodha.e_com.security;

import com.yodha.e_com.entities.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class AppUserDetails implements UserDetails {

    private final Users user;

    public AppUserDetails(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return user.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public boolean hasRole(String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    public List<String> getRoles() {
        return  user.getRoles();
    }


}
