package org.goormthon.beotkkotthon.rebook.usecase.challenge;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.challenge.ChallengeDto;

import java.util.UUID;

@UseCase
public interface ReadChallengeUseCase {
    ChallengeDto execute(UUID userId, Integer challengeId);
}
