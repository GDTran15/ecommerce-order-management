package com.duong.ecommerce.exception;

public class ResourceAlreadyExistedException extends RuntimeException {

    public ResourceAlreadyExistedException(String message) {
        super(message);
    }

}
