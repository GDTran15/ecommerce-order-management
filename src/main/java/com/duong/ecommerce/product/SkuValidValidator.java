package com.duong.ecommerce.product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SkuValidValidator implements ConstraintValidator<SkuValid,String> {

    public boolean isValid(String value, ConstraintValidatorContext context){
        if (value == null) return true;

        return value.matches("^([A-Z]{3})-(\\d{4})$");
    }
}
