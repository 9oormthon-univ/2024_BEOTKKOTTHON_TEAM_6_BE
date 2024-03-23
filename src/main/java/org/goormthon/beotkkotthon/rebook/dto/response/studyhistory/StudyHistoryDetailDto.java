package org.goormthon.beotkkotthon.rebook.dto.response.studyhistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "공부 기록 상세 정보")
public class StudyHistoryDetailDto extends SelfValidating<StudyHistoryDetailDto> {
    @NotNull
    @Schema(description = "공부 기록 사진 url", example = "https://rebook.s3.ap-northeast-2.amazonaws.com/image.jpg")
    private final String imageUrl;

    @NotNull
    @Schema(description = "재활용 카테고리에 대한 설명", example = "페트병은 라벨을 떼고 분리 수거해야 합니다.")
    private final String content;

    @NotNull
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
    @Schema(description = "생성일", example = "2021-10-01")
    private final String createdAt;

    @Builder
    public StudyHistoryDetailDto(
            String imageUrl,
            String content,
            String createdAt
    ) {
        this.imageUrl = imageUrl;
        this.content = content;
        this.createdAt = createdAt;
        validateSelf();
    }
}
