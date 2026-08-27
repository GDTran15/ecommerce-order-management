package com.duong.ecommerce.order.dto;

import com.duong.ecommerce.order.Order;
import com.duong.ecommerce.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderDTOResponse(
        BigDecimal totalAmount,
        OrderStatus status,
        Long customerId,
        List<OrderItemDTOResponse> orderItem



) {
}
