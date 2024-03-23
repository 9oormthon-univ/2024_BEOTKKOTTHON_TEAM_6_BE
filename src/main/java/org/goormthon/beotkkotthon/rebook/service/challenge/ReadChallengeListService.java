package org.goormthon.beotkkotthon.rebook.service.challenge;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.Challenge;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.challenge.ChallengeListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.challenge.ReadChallengeListUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadChallengeListService implements ReadChallengeListUseCase {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public List<ChallengeListDto> execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        // 존재하는 챌린지를 모두 조회
        List<Challenge> challenges = challengeRepository.findAll();
        List<Integer> challengeIds = challenges.stream()
                .map(Challenge::getId)
                .toList();

        // 유저가 참여한 챌린지들만 조회
        List<ChallengeRepository.UserParticipateChallenge> userParticipateChallenges
                = challengeRepository.findAllByUserParticipateInId(user, challengeIds);

        List<ChallengeListDto> challengeListDtos = new ArrayList<>();

        // 현재 시간을 기준으로 참여한 챌린지인지 확인하는 용도
        LocalDateTime now = LocalDateTime.now();

        for (Challenge challenge : challenges) {
            ChallengeRepository.UserParticipateChallenge userParticipateChallenge = userParticipateChallenges.stream()
                    .filter(item -> item.getChallengeId().equals(challenge.getId()))
                    .findFirst()
                    .orElse(null);

            if (userParticipateChallenge != null) {
                challengeListDtos.add(ChallengeListDto.builder()
                        .id(challenge.getId())
                        .roomId(userParticipateChallenge.getChallengeRoomId())
                        .isCurrentParticipate(now.isBefore(userParticipateChallenge.getDeadlineAt()))
                        .build());
            } else {
                challengeListDtos.add(ChallengeListDto.builder()
                        .id(challenge.getId())
                        .build());
            }
        }

        return challengeListDtos;
    }
}
