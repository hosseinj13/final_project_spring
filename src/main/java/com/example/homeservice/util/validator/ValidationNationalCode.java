package com.example.homeservice.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidNationalCode.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationNationalCode {
    String message() default "Invalid nation code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
