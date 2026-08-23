package com.duong.ecommerce.dto.order.request;

public record OrderItemDTORequest(
        String  sku,
        Integer quantity
) {
}
