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
    @Schema(description = "퀴즈 기록 ID", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "사용자가 입력한 답", example = "true")
    private final Boolean userAnswer;

    @NotNull
    @Schema(description = "실제 정답", example = "true")
    private final Boolean validAnswer;

    @Builder
    public QuizHistoryResultDto(
            Integer id,
            Boolean userAnswer,
            Boolean validAnswer
    ) {
        this.id = id;
        this.userAnswer = userAnswer;
        this.validAnswer = validAnswer;
        this.validateSelf();
    }
}
