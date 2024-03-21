package org.goormthon.beotkkotthon.rebook.usecase.challenge;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.ChallengeListDto;

import java.util.List;
import java.util.UUID;

@UseCase
public interface ReadChallengeListUseCase {
    List<ChallengeListDto> execute(UUID userId);
}
