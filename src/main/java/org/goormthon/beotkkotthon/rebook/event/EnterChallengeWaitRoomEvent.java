package org.goormthon.beotkkotthon.rebook.event;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EnterChallengeWaitRoomEvent(
        UUID userId,
        Integer challengeId
) {
}
