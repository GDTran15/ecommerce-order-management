package com.duong.ecommerce.product.dto;

import java.math.BigDecimal;

public record ProductDTOResponse(
        Long productId,
        String productName,
        String sku,
        String productDesc,
        Integer quantity,
        BigDecimal price

) {
}
