package com.duong.ecommerce.product;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SkuValidValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.RECORD_COMPONENT,ElementType.FIELD,ElementType.PARAMETER})
public @interface SkuValid {

    String message() default "Sku pattern invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
