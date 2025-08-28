package com.yodha.e_com.services;

import com.yodha.e_com.dto.ItemRequestDto;
import com.yodha.e_com.dto.ItemResponseDto;
import com.yodha.e_com.dto.OrderRequestDTO;
import com.yodha.e_com.dto.OrderResponseDTO;
import com.yodha.e_com.entities.Order;
import com.yodha.e_com.entities.OrderItem;
import com.yodha.e_com.entities.Payment;
import com.yodha.e_com.entities.Product;
import com.yodha.e_com.exception.ResourceNotFoundException;
import com.yodha.e_com.mapper.OrderMapper;
import com.yodha.e_com.mapper.PaymentRepository;
import com.yodha.e_com.repository.OrderRepository;
import com.yodha.e_com.repository.ProductRepo;
import com.yodha.e_com.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository, UserRepo userRepo, ProductRepo productRepo, OrderMapper orderMapper, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.paymentRepository = paymentRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, String email) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for (ItemRequestDto itemDto : orderRequestDTO.getItems()) {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + itemDto.getProductId()));

            if (itemDto.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);

            // Deduct stock and save product
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
        Order savedOrder = orderRepository.save(order);
        payment.setOrderId(savedOrder.getId());
        paymentRepository.save(payment);

        List<ItemResponseDto> itemResponseDtos = savedOrder.getItems()
                .stream()
                .map(orderMapper::toItemResponseDto)
                .toList();

        OrderResponseDTO orderResponseDTO = orderMapper.toOrderResponseDTO(savedOrder);
        orderResponseDTO.setItems(itemResponseDtos);
        orderResponseDTO.setPaymentUrl(
                String.format("http://localhost:8080/api/v1/payments/mock/%s",
                        savedOrder.getPaymentId() != null ? savedOrder.getPaymentId().toString() : "")
        );
        return orderResponseDTO;
    }

    public List<OrderResponseDTO> getOrdersByUser(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        if (orders.isEmpty()) {
           throw  new ResourceNotFoundException("No orders found for user with email: " + email);
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
}
