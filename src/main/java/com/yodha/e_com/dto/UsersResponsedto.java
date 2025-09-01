package com.yodha.e_com.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsersResponsedto {
    private String email;
    private String name;
    private List<String> roles;
}
