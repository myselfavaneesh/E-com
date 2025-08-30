package com.yodha.e_com.mapper;


import com.yodha.e_com.dto.ItemResponseDto;
import com.yodha.e_com.dto.OrderResponseDTO;
import com.yodha.e_com.dto.OrderStatusResponseDTO;
import com.yodha.e_com.entities.Order;
import com.yodha.e_com.entities.OrderItem;
import com.yodha.e_com.entities.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "paymentUrl", ignore = true)
    @Mapping(target = "paymentId", expression = "java(order.getPaymentId() != null ? order.getPaymentId().toString() : null)")
    OrderResponseDTO toOrderResponseDTO(Order order);

    ItemResponseDto toItemResponseDto(OrderItem item);

    @Mapping(target = "orderId", expression = "java(orderStatus.getOrderId() != null ? orderStatus.getOrderId().toString() : null)")
    OrderStatusResponseDTO toOrderStatusResponseDTO(OrderStatus orderStatus);

}
