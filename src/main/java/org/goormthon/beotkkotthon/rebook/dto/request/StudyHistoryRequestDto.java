package org.goormthon.beotkkotthon.rebook.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "공부 기록 북마크 상태 정보")
public record StudyHistoryRequestDto(
        @NotNull
        @Schema(description = "공부 기록 북마크 상태", example = "true")
        Boolean isMarking
) {
}
