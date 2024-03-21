package org.goormthon.beotkkotthon.rebook.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

@Getter
@Schema(description = "도전방 스터디 히스토리")
public class ChallengeRoomStudyHistoryDto extends SelfValidating<ChallengeRoomStudyHistoryDto> {
    @NotNull
    @Schema(description = "도전방 스터디 히스토리 ID", example = "1")
    private final Integer id;

    @NotNull
    @Schema(description = "도전방 카테고리", example = "Paper")
    private final String category;

    @Builder
    public ChallengeRoomStudyHistoryDto(Integer id, String category) {
        this.id = id;
        this.category = category;
        this.validateSelf();
    }
}
