package com.duong.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    @Min(value = 0)
    private BigDecimal productPrice;
    @Min(value = 0)
    private Integer productQuantity;

}
