package com.yodha.e_com.controller;


import com.yodha.e_com.services.PaymentService;
import com.yodha.e_com.utils.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/mock/{paymentId}")
    public String mockPayment(@PathVariable String paymentId, Model model) {
        var payment = paymentService.getPaymentByID(new ObjectId(paymentId));
        model.addAttribute("paymentId", paymentId);
        model.addAttribute("amount", payment.getAmount());
        model.addAttribute("orderId", payment.getOrderId());
        return "mock-payment";
    }

    @GetMapping("/callback/{paymentId}/{status}")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> paymentCallback(@PathVariable String paymentId, @PathVariable String status) {
        return ResponseEntity.ok(paymentService.processPayment(new ObjectId(paymentId), status));
    }

    @GetMapping("/payment/result")
    public String showPaymentResult(
            @RequestParam String paymentId,
            @RequestParam String orderId,
            @RequestParam String amount,
            @RequestParam Boolean success,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) String method,
            Model model) {

        model.addAttribute("paymentId", paymentId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("success", success);
        model.addAttribute("message", message != null ? message :
                (success ? "Payment completed successfully!" : "Payment failed. Please try again."));
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("method", method);
        model.addAttribute("timestamp", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")));

        return "payment-result";
    }
}


