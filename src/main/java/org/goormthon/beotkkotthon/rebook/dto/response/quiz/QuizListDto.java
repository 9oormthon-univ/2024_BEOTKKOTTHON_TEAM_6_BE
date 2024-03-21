package org.goormthon.beotkkotthon.rebook.dto.response.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "랜덤 퀴즈 목록 정보")
public class QuizListDto extends SelfValidating<QuizListDto> {
    @NotNull
    @Schema(description = "랜덤 퀴즈 번호", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "랜덤 퀴즈의 재활용 카테고리", example = "종이")
    private final String category;

    @Builder
    public QuizListDto(Integer id, String category) {
        this.id = id;
        this.category = category;
        validateSelf();
    }
}
