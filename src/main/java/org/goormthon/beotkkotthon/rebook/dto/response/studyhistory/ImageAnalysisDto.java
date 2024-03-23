package org.goormthon.beotkkotthon.rebook.dto.response.studyhistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "분석된 사진 정보")
public class ImageAnalysisDto extends SelfValidating<ImageAnalysisDto> {
    @NotNull
    @Schema(description = "오늘의 챌린지 완료 요부", example = "true")
    private final Boolean completeTodayCurrentChallenge;

    @NotNull
    @Schema(description = "분석된 사진의 재활용 카테고리", example = "종이")
    private final String recycleCategory;

    @NotNull
    @Schema(description = "분석된 사진의 재활용 카테고리에 대한 설명", example = "페트병은 라벨을 떼고 분리 수거해야 합니다.")
    private final String information;

    @NotNull
    @Schema(description = "사진 등록 날짜")
    private final String created_at;

    @Builder
    public ImageAnalysisDto(Boolean completeTodayCurrentChallenge, String recycleCategory, String information, String created_at) {
        this.completeTodayCurrentChallenge = completeTodayCurrentChallenge;
        this.recycleCategory = recycleCategory;
        this.information = information;
        this.created_at = created_at;
        validateSelf();
    }
}
