package com.yodha.e_com.entities;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blocked_Token")
@Data
public class BlockedToken {
    private String token;
}

