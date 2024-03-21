package org.goormthon.beotkkotthon.rebook.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "알림 시간 정보")
public record UserNotificationTimeRequestDto(
        @NotNull
        @Schema(description = "알림 설정 시간: 시", example = "9")
        String notificationHour,

        @NotNull
        @Schema(description = "알림 설정 시간: 분", example = "15")
        String notificationMinute
) {
}
