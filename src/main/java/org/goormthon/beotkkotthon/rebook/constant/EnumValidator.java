package org.goormthon.beotkkotthon.rebook.constant;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.goormthon.beotkkotthon.rebook.annotation.Enum;

public class EnumValidator implements ConstraintValidator<Enum, java.lang.Enum> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(java.lang.Enum value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues == null) {
            return false;
        }

        for (Object enumValue : enumValues) {
            if (value == enumValue) {
                return true;
            }
        }

        return false;
    }
}
