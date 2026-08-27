package com.duong.ecommerce.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Long save(Product pro);

    Optional<Product> getBySku(String sku);

    List<Product> getAll(int page, int size);

    Optional<Product> findById(Long productId);

    boolean existedBySku(String sku);

    void update(Product product);

    boolean existedById(Long productId);

    void deleteById(Long productId);

    Optional<Product> findBySkuForUpdate(String sku);

    Optional<Product> findByIdForUpdate(Long productId);
}
