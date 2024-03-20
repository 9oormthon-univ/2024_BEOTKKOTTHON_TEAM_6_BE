package org.goormthon.beotkkotthon.rebook.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공부 기록 북마크 상태 정보")
public class StudyHistoryRequestDto {
    @Schema(description = "공부 기록 북마크 상태", example = "true")
    private Boolean isMarking;
}
