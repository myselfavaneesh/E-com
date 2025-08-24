package com.yodha.e_com.entities;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categorys")
@Data
public class Category {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String categoryName;
    @NonNull
    private String description;
}
