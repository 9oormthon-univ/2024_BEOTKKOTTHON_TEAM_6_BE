package org.goormthon.beotkkotthon.rebook.dto.response;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
public class UserDetailDto extends SelfValidating<JwtTokenDto> {
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하여야 합니다.")
    private final String nickname;

    @Builder
    public UserDetailDto(String nickname) {
        this.nickname = nickname;
        this.validateSelf();
    }
}
