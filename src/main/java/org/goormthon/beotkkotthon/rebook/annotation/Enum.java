package org.goormthon.beotkkotthon.rebook.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.goormthon.beotkkotthon.rebook.constant.EnumValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Enum {
    String message() default "Invalid Enum Value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum<?>> enumClass();
}
