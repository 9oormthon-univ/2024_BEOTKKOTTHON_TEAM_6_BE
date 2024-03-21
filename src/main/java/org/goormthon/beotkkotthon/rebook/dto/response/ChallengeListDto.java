package org.goormthon.beotkkotthon.rebook.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;
import org.springframework.lang.Nullable;

@Getter
public class ChallengeListDto extends SelfValidating<ChallengeListDto> {
    @NotNull
    private final Integer id;

    @Nullable
    private final Integer roomId;

    @Nullable
    private final Boolean isCurrentParticipate;

    @Builder
    public ChallengeListDto(Integer id, Integer roomId, Boolean isCurrentParticipate) {
        this.id = id;
        this.roomId = roomId;
        this.isCurrentParticipate = isCurrentParticipate;
        this.validateSelf();
    }
}
