package org.goormthon.beotkkotthon.rebook.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "알림 설정 상태 정보")
public record UserNotificationRequestDto(
        @NotNull
        @Schema(description = "알림 설정 상태 정보", example = "true")
        Boolean isActiveNotification
) {
}
