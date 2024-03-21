package org.goormthon.beotkkotthon.rebook.service.challenge;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.Challenge;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.ChallengeDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRepository;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRoomRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.challenge.ReadChallengeUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadChallengeService implements ReadChallengeUseCase {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeRoomRepository challengeRoomRepository;

    @Override
    public ChallengeDto execute(UUID userId, Integer challengeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Boolean isUserParticipateInChallenge
                = challengeRoomRepository.isUserParticipateInChallenge(user, LocalDateTime.now());

        return ChallengeDto.builder()
                .description(challenge.getDescription())
                .canParticipate(!isUserParticipateInChallenge)
                .build();
    }
}
