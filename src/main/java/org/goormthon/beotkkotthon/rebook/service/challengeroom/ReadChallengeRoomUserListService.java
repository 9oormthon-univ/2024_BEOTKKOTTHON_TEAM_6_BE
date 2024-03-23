package org.goormthon.beotkkotthon.rebook.service.challengeroom;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoom;
import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoomStudyHistory;
import org.goormthon.beotkkotthon.rebook.dto.response.challengeroom.ChallengeRoomStudyHistoryDto;
import org.goormthon.beotkkotthon.rebook.dto.response.challengeroom.ChallengeRoomUserDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRoomRepository;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRoomStudyHistoryRepository;
import org.goormthon.beotkkotthon.rebook.usecase.challengeroom.ReadChallengeRoomUserListUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadChallengeRoomUserListService implements ReadChallengeRoomUserListUseCase {
    private final ChallengeRoomRepository challengeRoomRepository;
    private final ChallengeRoomStudyHistoryRepository challengeRoomStudyHistoryRepository;

    @Override
    public List<ChallengeRoomUserDto> execute(Integer challengeRoomId) {
        ChallengeRoom challengeRoom = challengeRoomRepository.findById(challengeRoomId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<ChallengeRoomStudyHistory> challengeRoomStudyHistories
                = challengeRoomStudyHistoryRepository.findAllByChallengeRoom(challengeRoom);

        return challengeRoom.getChallengeRoomUsers().stream()
                .map(challengeRoomUser -> {
                    List<ChallengeRoomStudyHistory> userStudyHistories = challengeRoomStudyHistories.stream()
                            .filter(challengeRoomStudyHistory -> challengeRoomStudyHistory.getStudyHistory().getUser().getId().equals(challengeRoomUser.getUser().getId()))
                            .toList();

                    return ChallengeRoomUserDto.builder()
                            .userId(challengeRoomUser.getUser().getId())
                            .userNickname(challengeRoomUser.getUser().getNickname())
                            .studyHistories(userStudyHistories.stream()
                                    .map(challengeRoomStudyHistory -> ChallengeRoomStudyHistoryDto.builder()
                                            .id(challengeRoomStudyHistory.getId())
                                            .category(challengeRoomStudyHistory.getStudyHistory().getRecycleCategory().getName())
                                            .build())
                                    .toList())
                            .build();
                })
                .toList();
    }
}
