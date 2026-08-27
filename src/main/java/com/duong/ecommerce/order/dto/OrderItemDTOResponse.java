package com.duong.ecommerce.order.dto;

import java.math.BigDecimal;

public record OrderItemDTOResponse(
        Long productId,
        String sku,
        String productName,
        BigDecimal productPrice,
        Integer quantity
) {
}
