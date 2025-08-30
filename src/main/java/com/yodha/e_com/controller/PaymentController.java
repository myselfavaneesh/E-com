package com.yodha.e_com.controller;


import com.yodha.e_com.services.PaymentService;
import com.yodha.e_com.utils.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/callback/{paymentId}/{status}")
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> paymentCallback(@PathVariable String paymentId, @PathVariable String status) {
        return ResponseEntity.ok(paymentService.processPayment(new ObjectId(paymentId), status));
    }
}


