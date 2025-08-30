package com.yodha.e_com.services;

import com.yodha.e_com.entities.Order;
import com.yodha.e_com.entities.Payment;
import com.yodha.e_com.entities.Product;
import com.yodha.e_com.mapper.PaymentRepository;
import com.yodha.e_com.repository.OrderRepository;
import com.yodha.e_com.repository.ProductRepo;
import com.yodha.e_com.utils.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;
    private final ProductRepo productRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository, ProductRepo productRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    public Payment getPaymentByID(ObjectId paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }

    public ApiResponse<String> processPayment(ObjectId paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with ID: " + paymentId));

        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + payment.getOrderId()));

        String responseMessage;
        boolean isSuccess = true;

        switch (status.toLowerCase()) {
            case "success" -> {
                payment.setStatus(Payment.Status.COMPLETED);
                order.setStatus(Order.Status.CONFIRMED);
                responseMessage = "Payment Successful";
            }
            case "fail" -> {
                payment.setStatus(Payment.Status.FAILED);
                order.setStatus(Order.Status.CANCELLED);
                order.getItems().forEach(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    product.setStock(product.getStock() + item.getQuantity());
                    productRepository.save(product);
                });
                responseMessage = "Payment Failed";
                isSuccess = false;
            }
            default -> throw new IllegalArgumentException("Invalid status parameter: " + status);
        }
        paymentRepository.save(payment);
        orderRepository.save(order);
        return new ApiResponse<>(isSuccess, responseMessage, null);
    }


}
