package com.duong.ecommerce.product;

import com.duong.ecommerce.product.dto.ProductDTOResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductToProductDTOResponse implements Function<Product, ProductDTOResponse> {
    @Override
    public ProductDTOResponse apply(Product product) {
        return new ProductDTOResponse(product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice());
    }
}
