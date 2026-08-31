package com.duong.ecommerce.product;

public class ResourceAlreadyExistedException extends RuntimeException {

    public ResourceAlreadyExistedException(String message) {
        super(message);
    }

}
