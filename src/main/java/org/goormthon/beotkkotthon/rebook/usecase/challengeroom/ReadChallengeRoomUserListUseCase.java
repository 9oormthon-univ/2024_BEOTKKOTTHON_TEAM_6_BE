package org.goormthon.beotkkotthon.rebook.usecase.challengeroom;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.challengeroom.ChallengeRoomUserDto;

import java.util.List;

@UseCase
public interface ReadChallengeRoomUserListUseCase {
    List<ChallengeRoomUserDto> execute(Integer challengeRoomId);
}
