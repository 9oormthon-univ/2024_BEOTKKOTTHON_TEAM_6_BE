package org.goormthon.beotkkotthon.rebook.dto.response.quizhistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "퀴즈 기록 생설 결과")
public class QuizHistoryResultDto extends SelfValidating<QuizHistoryResultDto> {
    @NotNull
    @Schema(description = "퀴즈 결과", example = "true")
    private final Boolean result;

    @Builder
    public QuizHistoryResultDto(
            Boolean result
    ) {
        this.result = result;
        this.validateSelf();
    }
}
