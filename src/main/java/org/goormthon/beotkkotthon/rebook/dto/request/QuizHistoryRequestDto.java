package org.goormthon.beotkkotthon.rebook.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "퀴즈 기록 생성 요청")
public record QuizHistoryRequestDto(
        @NotNull
        @Schema(description = "퀴즈 ID", example = "1")
        Integer quizId,
        @NotNull
        @Schema(description = "사용자 정답", example = "true")
        Boolean answer
) {
}
