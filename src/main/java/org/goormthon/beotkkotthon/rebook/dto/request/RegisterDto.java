package org.goormthon.beotkkotthon.rebook.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력해주세요.")
        String serialId,

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 20자 이하로 입력해주세요.")
        String password,

        @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
        String nickname
) {
}
