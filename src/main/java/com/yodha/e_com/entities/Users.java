package com.yodha.e_com.entities;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class Users {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private List<String> roles;
}


