package com.yodha.e_com.services;

import com.yodha.e_com.dto.*;
import com.yodha.e_com.entities.*;
import com.yodha.e_com.exception.ResourceNotFoundException;
import com.yodha.e_com.mapper.OrderMapper;
import com.yodha.e_com.mapper.PaymentRepository;
import com.yodha.e_com.repository.OrderRepository;
import com.yodha.e_com.repository.OrderStatusRepository;
import com.yodha.e_com.repository.ProductRepo;
import com.yodha.e_com.utils.Mail;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepo productRepo;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final Mail mail;

    public OrderService(OrderRepository orderRepository, ProductRepo productRepo, OrderMapper orderMapper, PaymentRepository paymentRepository
            , OrderStatusRepository orderStatusRepository, Mail mail) {
        this.orderRepository = orderRepository;
        this.productRepo = productRepo;
        this.paymentRepository = paymentRepository;
        this.orderMapper = orderMapper;
        this.orderStatusRepository = orderStatusRepository;
        this.mail = mail;
    }

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO, String email) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for (ItemRequestDto itemDto : orderRequestDTO.getItems()) {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + itemDto.getProductId()));

            if (itemDto.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Check if the product already exists in orderItems
            OrderItem existingOrderItem = orderItems.stream()
                    .filter(item -> item.getProductId().equals(product.getId()))
                    .findFirst()
                    .orElse(null);

            if (existingOrderItem != null) {
                existingOrderItem.setQuantity(existingOrderItem.getQuantity() + itemDto.getQuantity());
            } else {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(product.getId());
                orderItem.setProductName(product.getName());
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItems.add(orderItem);
            }

            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepo.save(product);
        }

        order.setEmail(email);
        order.setItems(orderItems);
        order.setTotalAmount(orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderRequestDTO.getShippingAddress());

        Payment payment = new Payment();
        if (Payment.Method.valueOf(orderRequestDTO.getPaymentMethod()).equals(Payment.Method.COD)) {
            order.setStatus(Order.Status.CONFIRMED);
        } else {
            order.setStatus(Order.Status.PENDING);
        }

        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(Payment.Method.valueOf(orderRequestDTO.getPaymentMethod()));
        payment.setStatus(Payment.Status.PENDING);
        payment = paymentRepository.save(payment);
        order.setPaymentId(payment.getId());

        String trackingNumber = "TRACK" + new ObjectId().toString().substring(0, 8).toUpperCase();
        order.setTrackingNumber(trackingNumber);

        Order savedOrder = orderRepository.save(order);

        payment.setOrderId(savedOrder.getId());
        paymentRepository.save(payment);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(savedOrder.getId());
        orderStatus.setTrackingNumber(trackingNumber);
        orderStatus.setStatus(savedOrder.getStatus().name());
        orderStatus.setUpdatedAt(LocalDateTime.now());
        orderStatusRepository.save(orderStatus);

        List<ItemResponseDto> itemResponse = savedOrder.getItems().stream()
                .map(orderMapper::toItemResponseDto)
                .toList();

        OrderResponseDTO orderResponseDTO = orderMapper.toOrderResponseDTO(savedOrder);
        orderResponseDTO.setItems(itemResponse);
        if (savedOrder.getStatus() == Order.Status.PENDING) {
            orderResponseDTO.setPaymentUrl(
                    String.format("http://localhost:8080/api/v1/payments/mock/%s",
                            savedOrder.getPaymentId() != null ? savedOrder.getPaymentId().toString() : "")
            );
        }

        mail.sendEmail(email, "Order Placed Successfully",
                "Your order has been placed successfully. Your tracking number is: " + trackingNumber);

        return orderResponseDTO;
    }


    public List<OrderResponseDTO> getOrdersByUser(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for user with email: " + email);
        }

        return orders.stream().map(order -> {
            List<ItemResponseDto> itemResponseDtos = order.getItems()
                    .stream()
                    .map(orderMapper::toItemResponseDto)
                    .toList();
            OrderResponseDTO orderResponseDTO = orderMapper.toOrderResponseDTO(order);
            orderResponseDTO.setItems(itemResponseDtos);
            return orderResponseDTO;
        }).toList();
    }

    public OrderStatusResponseDTO trackOrder(String trackingNumber) {
        Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findByTrackingNumber(trackingNumber);
        if (orderStatusOpt.isEmpty()) {
            throw new ResourceNotFoundException("No order found with tracking number: " + trackingNumber);
        }
        return orderMapper.toOrderStatusResponseDTO(orderStatusOpt.get());
    }

    public void cancelOrder(ObjectId Id, String email) {
        Optional<Order> orderOpt = orderRepository.findById(Id);

        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("No order found with ID: " + Id);
        } else if (orderOpt.get().getStatus() == Order.Status.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        } else if (!orderOpt.get().getEmail().equals(email)) {
            throw new RuntimeException("You are not authorized to cancel this order");
        } else if (orderOpt.get().getStatus() == Order.Status.DELIVERED) {
            throw new RuntimeException("Delivered orders cannot be cancelled");
        }

        orderOpt.get().setStatus(Order.Status.CANCELLED);
        paymentRepository.findById(orderOpt.get().getPaymentId()).ifPresent(
                payment -> {
                    payment.setStatus(Payment.Status.CANCELLED);
                    paymentRepository.save(payment);
                }
        );
        orderStatusRepository.findByTrackingNumber(orderOpt.get().getTrackingNumber()).ifPresent(
                orderStatus -> {
                    orderStatus.setStatus(Order.Status.CANCELLED.name());
                    orderStatus.setUpdatedAt(LocalDateTime.now());
                    orderStatusRepository.save(orderStatus);
                }
        );
        orderRepository.save(orderOpt.get());
    }

    public void updateOrderStatus(ObjectId orderId, Order.Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("No order found with ID: " + orderId));
        if (!java.util.Arrays.asList(Order.Status.values()).contains(status)) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        order.setStatus(status);
        orderRepository.save(order);
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(order.getId());
        orderStatus.setTrackingNumber(order.getTrackingNumber());
        orderStatus.setStatus(status.name());
        orderStatus.setUpdatedAt(LocalDateTime.now());
        orderStatusRepository.save(orderStatus);
    } //Admin Level
}
