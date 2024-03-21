package org.goormthon.beotkkotthon.rebook.usecase.matching;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingMessageDto;

import java.util.UUID;

@UseCase
public interface EnterChallengeWaitRoomUseCase {
    MatchingMessageDto execute(UUID userId, Integer challengeId);
}
