package com.yodha.e_com.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Payment {
    @Id
    private ObjectId id;
    private int amount;
    private enum status{
        SUCCESS,
        FAILED
    }
    private String transaction_id;
}
