package org.goormthon.beotkkotthon.rebook.dto.response.challenge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;
import org.springframework.lang.Nullable;

@Getter
@Schema(description = "챌린지 정보")
public class ChallengeDto extends SelfValidating<ChallengeDto> {
    @NotNull
    @Schema(description = "챌린지 설명", example = "튜토리얼 챌린지 1주차")
    private final String description;

    @Nullable
    @Schema(description = "참여 가능 여부", example = "true")
    private final Boolean canParticipate;

    @Builder
    public ChallengeDto(String description, Boolean canParticipate) {
        this.description = description;
        this.canParticipate = canParticipate;
        this.validateSelf();
    }
}
