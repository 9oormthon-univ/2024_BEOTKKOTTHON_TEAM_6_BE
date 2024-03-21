package org.goormthon.beotkkotthon.rebook.usecase.matching;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;

import java.util.UUID;

@UseCase
public interface LeaveChallengeWaitRoomUseCase {
    void execute(UUID userId, Integer challengeId);
}
