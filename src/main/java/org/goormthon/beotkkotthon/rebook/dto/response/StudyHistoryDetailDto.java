package org.goormthon.beotkkotthon.rebook.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "공부 기록 상세 정보")
public class StudyHistoryDetailDto extends SelfValidating<StudyHistoryDetailDto> {
    @Schema(description = "공부 기록 사진 url", example = "https://rebook.s3.ap-northeast-2.amazonaws.com/image.jpg")
    private final String imageUrl;

    @Schema(description = "재활용 카테고리에 대한 설명", example = "페트병은 라벨을 떼고 분리 수거해야 합니다.")
    private final String content;

    @Builder
    public StudyHistoryDetailDto(String imageUrl, String content) {
        this.imageUrl = imageUrl;
        this.content = content;
        validateSelf();
    }
}
