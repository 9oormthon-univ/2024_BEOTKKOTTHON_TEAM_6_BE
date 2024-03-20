package org.goormthon.beotkkotthon.rebook.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "사용자 상세 정보")
public class UserDetailDto extends SelfValidating<JwtTokenDto> {
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하여야 합니다.")
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private final String nickname;

    @Builder
    public UserDetailDto(String nickname) {
        this.nickname = nickname;
        this.validateSelf();
    }
}
