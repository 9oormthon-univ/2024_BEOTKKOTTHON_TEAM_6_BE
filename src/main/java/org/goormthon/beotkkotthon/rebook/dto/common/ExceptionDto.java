package org.goormthon.beotkkotthon.rebook.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;

@Getter
public sealed class ExceptionDto permits ArgumentNotValidExceptionDto {
    @NotNull
    private final Integer code;

    @NotNull private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
