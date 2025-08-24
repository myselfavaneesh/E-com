package com.yodha.e_com.entities;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cartItem")
@Data
public class CartItem {
    @Id
    private ObjectId id;
    @DBRef
    private Cart cartId;
    @DBRef
    private Products productId;
    private int quantity;
}
