package com.duong.ecommerce.product.dto;

import com.duong.ecommerce.common.validation.OnCreate;
import com.duong.ecommerce.common.validation.OnUpdate;
import com.duong.ecommerce.product.SkuValid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank(groups = OnCreate.class)
        String productName,
        @NotBlank(groups = OnCreate.class)
        @SkuValid(groups = {OnCreate.class, OnUpdate.class})
        String sku,
        @NotBlank(groups = OnCreate.class)
        String productDescription,
        @NotNull(groups = OnCreate.class)
        @Min(value = 1, groups = {OnCreate.class,OnUpdate.class})
        BigDecimal productPrice,
        @Positive(groups = {OnCreate.class, OnUpdate.class})
        Integer productQuantity

) {
}
