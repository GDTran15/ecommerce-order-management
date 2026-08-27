package com.duong.ecommerce.order.dto;

public record OrderItemDTORequest(
        String  sku,
        Integer quantity
) {
}
