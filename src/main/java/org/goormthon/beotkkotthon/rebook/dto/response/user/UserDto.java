package org.goormthon.beotkkotthon.rebook.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;
import org.goormthon.beotkkotthon.rebook.dto.response.JwtTokenDto;

@Getter
@Schema(description = "사용자 정보")
public class UserDto extends SelfValidating<JwtTokenDto> {
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상 20자 이하여야 합니다.")
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private final String nickname;

    @NotNull
    @Schema(description = "사용자 시리얼 번호", example = "1")
    private final String code;

    @Size(min = 0, max = 100, message = "환경 온도는 0도 이상 100도 이하여야 합니다.")
    @Schema(description = "환경 온도", example = "36.5")
    private final String environmentalTemperature;

    @NotNull
    @Schema(description = "알림 설정 여부", example = "true")
    private final Boolean isActiveNotification;

    @Size(min = 0, max = 23, message = "알림 설정 시간(시)은 0 이상 23 이하여야 합니다.")
    @Schema(description = "알림 설정 시간: 시", example = "9")
    private final Integer notificationHour;

    @Size(min = 0, max = 59, message = "알림 설정 시간(분)은 0 이상 59 이하여야 합니다.")
    @Schema(description = "알림 설정 시간: 분", example = "15")
    private final Integer notificationMinute;

    @Builder
    public UserDto(String nickname, String code, String environmentalTemperature, Boolean isActiveNotification, Integer notificationHour, Integer notificationMinute) {
        this.nickname = nickname;
        this.code = code;
        this.environmentalTemperature = environmentalTemperature;
        this.isActiveNotification = isActiveNotification;
        this.notificationHour = notificationHour;
        this.notificationMinute = notificationMinute;
        this.validateSelf();
    }
}
