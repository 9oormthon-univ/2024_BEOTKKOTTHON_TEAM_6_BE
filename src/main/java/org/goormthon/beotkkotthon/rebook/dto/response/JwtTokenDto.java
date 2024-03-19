package org.goormthon.beotkkotthon.rebook.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
public class JwtTokenDto extends SelfValidating<JwtTokenDto> {
    @NotNull(message = "AccessToken은 null이 될 수 없습니다.")
    private final String accessToken;

    @NotNull(message = "RefreshToken은 null이 될 수 없습니다.")
    private final String refreshToken;


    @Builder
    public JwtTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        validateSelf();
    }
}
