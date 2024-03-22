package org.goormthon.beotkkotthon.rebook.dto.response.quizhistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "퀴즈 기록 목록 조회 응답")
public class QuizHistoryListDto extends SelfValidating<QuizHistoryListDto> {
    @NotNull
    @Schema(description = "퀴즈 기록 ID", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "퀴즈 카테고리", example = "자연")
    private final String category;

    @NotNull
    @Schema(description = "퀴즈 결과", example = "true")
    private final Boolean result;

    @Builder
    public QuizHistoryListDto(
            Integer id,
            String category,
            Boolean result
    ) {
        this.id = id;
        this.category = category;
        this.result = result;
        this.validateSelf();
    }
}
