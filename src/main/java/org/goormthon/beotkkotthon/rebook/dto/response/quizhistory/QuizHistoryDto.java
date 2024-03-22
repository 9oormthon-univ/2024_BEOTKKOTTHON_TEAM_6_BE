package org.goormthon.beotkkotthon.rebook.dto.response.quizhistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "퀴즈 기록 조회 응답")
public class QuizHistoryDto extends SelfValidating<QuizHistoryDto> {
    @NotNull
    @Schema(description = "퀴즈 ID", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "퀴즈 카테고리", example = "자연")
    private final String category;

    @NotNull
    @Schema(description = "퀴즈 제목", example = "퀴즈 제목")
    private final String title;

    @NotNull
    @Schema(description = "퀴즈 내용", example = "퀴즈 내용")
    private final String content;

    @NotNull
    @Schema(description = "사용자 답변", example = "true")
    private final Boolean userAnswer;

    @NotNull
    @Schema(description = "퀴즈 정답", example = "false")
    private final Boolean validAnswer;

    @Builder
    public QuizHistoryDto(
            Integer id,
            String category,
            String title,
            String content,
            Boolean userAnswer,
            Boolean validAnswer
    ) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.userAnswer = userAnswer;
        this.validAnswer = validAnswer;
        this.validateSelf();
    }
}
