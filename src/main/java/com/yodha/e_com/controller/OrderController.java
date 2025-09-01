package com.yodha.e_com.controller;

import com.yodha.e_com.dto.OrderRequestDTO;
import com.yodha.e_com.dto.OrderResponseDTO;
import com.yodha.e_com.dto.OrderStatusResponseDTO;
import com.yodha.e_com.entities.Order;
import com.yodha.e_com.services.OrderService;
import com.yodha.e_com.utils.ApiResponse;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new ApiResponse<>(true, "Order created successfully", orderService.placeOrder(orderRequestDTO, email)));
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<ApiResponse<OrderStatusResponseDTO>> getOrderByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Order Location Fetched", orderService.trackOrder(trackingNumber)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrdersOfUsers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched successfully", orderService.getOrdersByUser(email)));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse<String>> CancelOrder(@PathVariable String orderId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.cancelOrder(new ObjectId(orderId), email);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order cancelled successfully", null));
    }

    @PatchMapping("/{orderId}/{status}")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus(@PathVariable String orderId, @PathVariable String status) {
        orderService.updateOrderStatus(new ObjectId(orderId), Order.Status.valueOf(status));
        return ResponseEntity.ok(new ApiResponse<>(true, "Order status updated successfully", null));
    }
}
