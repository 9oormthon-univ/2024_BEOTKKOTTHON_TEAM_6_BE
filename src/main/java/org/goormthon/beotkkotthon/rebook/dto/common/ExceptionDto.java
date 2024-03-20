package org.goormthon.beotkkotthon.rebook.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;

@Getter
public sealed class ExceptionDto permits ArgumentNotValidExceptionDto {
    @NotNull(message = "예외 코드는 필수입니다.")
    private final Integer code;

    @NotNull(message = "예외 메시지는 필수입니다.")
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
