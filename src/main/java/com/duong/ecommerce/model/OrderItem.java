package com.duong.ecommerce.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    private Long id;
    private Long orderId;
    private Long productId;
    @Min(1)
    private Integer quantity;
    @Min(0)
    private BigDecimal unitPrice;


}