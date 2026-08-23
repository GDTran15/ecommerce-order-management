package com.duong.ecommerce.dto.order.response;

import java.math.BigDecimal;

public record OrderItemDTOResponse(
        Long productId,
        String sku,
        String productName,
        BigDecimal productPrice,
        Integer quantity
) {
}
