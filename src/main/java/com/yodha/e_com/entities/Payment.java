package com.yodha.e_com.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "payments")
public class Payment {
    private ObjectId id;
    private ObjectId orderId;
    private Method paymentMethod;
    private double amount;
    private Status status;

    public enum Method {
        CREDIT_CARD, DEBIT_CARD, UPI, BANK_TRANSFER, COD
    }

    public enum Status {
        PENDING, COMPLETED, FAILED, REFUNDED,CANCELLED
    }
}
