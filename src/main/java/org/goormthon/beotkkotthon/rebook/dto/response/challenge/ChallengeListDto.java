package org.goormthon.beotkkotthon.rebook.dto.response.challenge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;
import org.springframework.lang.Nullable;

@Getter
@Schema(description = "챌린지 목록 조회 응답")
public class ChallengeListDto extends SelfValidating<ChallengeListDto> {
    @NotNull
    @Schema(description = "챌린지 ID", example = "1")
    private final Integer id;

    @Nullable
    @Schema(description = "참여한 챌린지 방 ID", example = "1")
    private final Integer roomId;

    @Nullable
    @Schema(description = "현재 참여 중인 챌린지 여부", example = "true")
    private final Boolean isCurrentParticipate;

    @Builder
    public ChallengeListDto(Integer id, Integer roomId, Boolean isCurrentParticipate) {
        this.id = id;
        this.roomId = roomId;
        this.isCurrentParticipate = isCurrentParticipate;
        this.validateSelf();
    }
}
