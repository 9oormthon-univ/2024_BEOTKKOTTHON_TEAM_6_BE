package org.goormthon.beotkkotthon.rebook.dto.response.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "퀴즈 상세 정보")
public class QuizDetailDto extends SelfValidating<QuizDetailDto> {
    @NotNull
    @Schema(description = "퀴즈 제목", example = "치킨뼈 분리수거")
    private final String title;

    @NotNull
    @Schema(description = "퀴즈 설명", example = "치킨뼈는 일반 쓰레기이다.")
    private final String content;

    @NotNull
    @Schema(description = "퀴즈 카테고리", example = "Paper")
    private final String category;

    @Builder
    public QuizDetailDto(
            String title,
            String content,
            String category
    ) {
        this.title = title;
        this.content = content;
        this.category = category;
        validateSelf();
    }
}
