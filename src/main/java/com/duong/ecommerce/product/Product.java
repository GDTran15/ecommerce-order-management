package com.duong.ecommerce.product;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String sku;
    private String description;
    @Min(value = 0)
    private BigDecimal price;
    @Min(value = 0)
    private Integer quantity;

}
