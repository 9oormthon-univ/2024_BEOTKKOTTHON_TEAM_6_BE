package org.goormthon.beotkkotthon.rebook.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;

import java.util.List;
import java.util.UUID;

@Getter
@Schema(description = "챌린지 룸 유저 정보")
public class ChallengeRoomUserDto extends SelfValidating<ChallengeRoomUserDto> {

    @NotNull
    @Schema(description = "유저 아이디", example = "550e8400-e29b-41d4-a716-446655440000")
    private final String userId;

    @NotNull
    @Schema(description = "유저 닉네임", example = "코딩왕")
    private final String userNickname;

    @NotNull
    @Schema(description = "스터디 히스토리")
    private final List<ChallengeRoomStudyHistoryDto> studyHistories;

    @Builder
    public ChallengeRoomUserDto(
            UUID userId,
            String userNickname,
            List<ChallengeRoomStudyHistoryDto> studyHistories
    ) {
        this.userId = userId.toString();
        this.userNickname = userNickname;
        this.studyHistories = studyHistories;
        this.validateSelf();
    }
}
