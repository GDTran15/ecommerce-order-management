package com.duong.ecommerce.product;

import com.duong.ecommerce.exception.ResourceAlreadyExistedException;
import com.duong.ecommerce.product.dto.CreateProductDTO;
import com.duong.ecommerce.product.dto.ProductDTOResponse;
import com.duong.ecommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepo;
    private final ProductToProductDTOResponse productToProductDTOResponse;

    @Override
    public Long createProduct(CreateProductDTO dto) {
        Product product = Product.builder()
                .name(dto.productName())
                .description(dto.productDescription())
                .price(dto.productPrice())
                .sku(dto.sku())
                .quantity(dto.productQuantity()).build();

        return productRepo.save(product);
    }

    @Override
    public ProductDTOResponse getProductBySku(String sku) {
        Product product = productRepo.getBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + sku + ": not existed"));
        return productToProductDTOResponse.apply(product);
    }

    @Override
    public List<ProductDTOResponse> getAllProducts(int page,int size) {
        return productRepo.getAll(page,size).stream().map(productToProductDTOResponse).toList();
    }

    @Override
    public void updateProduct(CreateProductDTO dto, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not existed")
        );
        if (dto.productName() != null) {
            product.setName(dto.productName());
        }
        if (dto.productDescription() != null){
            product.setDescription(dto.productDescription());
        }
        if (dto.productQuantity() != null){
            product.setQuantity(dto.productQuantity());
        }
        if (dto.productPrice() != null ){
            product.setPrice(dto.productPrice());
        }
        if (dto.sku() != null){
            if (productRepo.existedBySku(dto.sku())){
                throw new ResourceAlreadyExistedException("Sku already existed");
            }
            product.setSku(dto.sku());
        }

        productRepo.update(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (productRepo.existedById(productId)){
            throw new ResourceNotFoundException("Product not found");
        }
        productRepo.deleteById(productId);
    }

    @Override
    public Optional<Product> findBySkuForUpdate(String sku) {
        return productRepo.findBySkuForUpdate(sku);
    }

    @Override
    public Optional<Product> findByIdForUpdate(Long productId) {
        return productRepo.findByIdForUpdate(productId);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productRepo.findById(productId);
    }

    @Override
    public void update(Product product) {
        productRepo.update(product);
    }


}
