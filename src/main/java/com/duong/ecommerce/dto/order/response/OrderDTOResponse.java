package com.duong.ecommerce.dto.order.response;

import com.duong.ecommerce.model.Order;
import com.duong.ecommerce.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderDTOResponse(
        BigDecimal totalAmount,
        OrderStatus status,
        Long customerId,
        List<OrderItemDTOResponse> orderItem



) {
}
