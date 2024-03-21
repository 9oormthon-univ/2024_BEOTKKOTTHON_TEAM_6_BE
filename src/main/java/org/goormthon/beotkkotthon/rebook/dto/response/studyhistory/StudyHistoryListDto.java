package org.goormthon.beotkkotthon.rebook.dto.response.studyhistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "공부 기록 목록 정보")
public class StudyHistoryListDto extends SelfValidating<StudyHistoryListDto> {
    @NotNull
    @Schema(description = "공부 기록 번호", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "공부 기록 사진 url", example = "https://rebook.s3.ap-northeast-2.amazonaws.com/image.jpg")
    private final String imageUrl;

    @NotNull
    @Schema(description = "북마크 여부", example = "true")
    private final Boolean isMarking;

    @Builder
    public StudyHistoryListDto(Integer id, String imageUrl, Boolean isMarking) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.isMarking = isMarking;
        validateSelf();
    }
}
