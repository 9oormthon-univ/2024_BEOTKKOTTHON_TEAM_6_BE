package org.goormthon.beotkkotthon.rebook.constant;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.annotation.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateValidator implements ConstraintValidator<Date, LocalDate> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(Date constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            // LocalDate 유형은 이미 LocalDate로 구문 분석되었으므로 추가 파싱이 필요하지 않습니다.
        } catch(DateTimeParseException e) {
            log.error("Invalid Date Format. (yyyy-MM-dd)");
            return false;
        }

        return true;
    }
}
