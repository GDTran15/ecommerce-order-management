package com.duong.ecommerce.product;

import com.duong.ecommerce.product.dto.CreateProductDTO;
import com.duong.ecommerce.product.dto.ProductDTOResponse;
import com.duong.ecommerce.common.validation.OnCreate;
import com.duong.ecommerce.common.validation.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<Void> createProduct(@RequestBody @Validated(OnCreate.class) CreateProductDTO dto){
        Long productId = productService.createProduct(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productId).toUri(); // check and study
        return ResponseEntity.created(location).build();

    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDTOResponse> getProduct(@PathVariable @SkuValid String sku){

        return ResponseEntity.ok(productService.getProductBySku( sku));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTOResponse>> getAllProduct(@RequestParam(defaultValue = "0")  int page, @RequestParam(defaultValue = "10") int size){
        List<ProductDTOResponse> list = productService.getAllProducts(page,size);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@RequestBody @Validated(OnUpdate.class) CreateProductDTO dto,@PathVariable Long productId){
        productService.updateProduct(dto,productId);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
