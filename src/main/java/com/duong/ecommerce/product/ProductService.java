package com.duong.ecommerce.product;

import com.duong.ecommerce.product.dto.CreateProductDTO;
import com.duong.ecommerce.product.dto.ProductDTOResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Long createProduct(CreateProductDTO dto);

    ProductDTOResponse getProductBySku(String sku);

    List<ProductDTOResponse> getAllProducts(int page, int size);

    void updateProduct(CreateProductDTO dto, Long productId);

    void deleteProduct(Long productId);

    Optional<Product> findBySkuForUpdate(String sku);

    Optional<Product> findByIdForUpdate(Long productId);

    Optional<Product> findById(Long productId);

    void update(Product product);
}
