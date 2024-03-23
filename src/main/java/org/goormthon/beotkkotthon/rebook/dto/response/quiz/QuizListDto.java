package org.goormthon.beotkkotthon.rebook.dto.response.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

import javax.annotation.Nullable;

@Getter
@Schema(description = "랜덤 퀴즈 목록 정보")
public class QuizListDto extends SelfValidating<QuizListDto> {
    @NotNull
    @Schema(description = "랜덤 퀴즈 번호", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "랜덤 퀴즈의 재활용 카테고리", example = "종이")
    private final String category;

    @Nullable
    @Schema(description = "풀었던 퀴즈 기록 번호", example = "1")
    private final Integer quizHistoryId;

    @Builder
    public QuizListDto(
            Integer id,
            String category,
            Integer quizHistoryId
    ) {
        this.id = id;
        this.category = category;
        this.quizHistoryId = quizHistoryId;
        validateSelf();
    }
}
