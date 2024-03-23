package org.goormthon.beotkkotthon.rebook.service.matching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.domain.Challenge;
import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoom;
import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoomUser;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.event.CompleteMatchingEvent;
import org.goormthon.beotkkotthon.rebook.event.EnterChallengeWaitRoomEvent;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRepository;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRoomRepository;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRoomUserRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeRoomRepository challengeRoomRepository;
    private final ChallengeRoomUserRepository challengeRoomUserRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final RedissonClient redissonClient;

    @Transactional
    @Async @EventListener
    public void execute(EnterChallengeWaitRoomEvent event) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        } finally {
            log.info("MatchingService.execute: {}", event);
        }

        List<String> userIds = new ArrayList<>();

        String resource = "resource_key";
        String key = Constants.CHALLENGE_KEY + event.challengeId();
        Integer challengeRoomId = null;

        RLock lock = redissonClient.getLock(resource);

        try {
            boolean isLocked = lock.tryLock(Constants.WAIT_TIMEOUT, Constants.LOCK_TIMEOUT + Constants.PLUS_TIMEOUT, TimeUnit.MILLISECONDS);

            if (!isLocked) {
                throw new InterruptedException();
            }

            RList<String> list = redissonClient.getList(key);

            if (list.size() < 2) {
                log.info("사용자가 2명 미만입니다.");
                throw new InterruptedException();
            }

            // 앞에  두사람을 뽑아오고 레디스 lsit에서 삭제
            String userIdOne = list.remove(0).substring(9);
            String userIdTwo = list.remove(0).substring(9);

            // 두 사용자를 매칭시킨다.
            User userOne = userRepository.findById(UUID.fromString(userIdOne))
                    .orElseThrow(InterruptedException::new);
            User userTwo = userRepository.findById(UUID.fromString(userIdTwo))
                    .orElseThrow(InterruptedException::new);
            Challenge challenge = challengeRepository.findById(event.challengeId())
                    .orElseThrow(InterruptedException::new);

            // 매칭된 사용자들을 챌린지 룸에 넣는다.
            ChallengeRoom challengeRoom = challengeRoomRepository.save(ChallengeRoom.builder()
                    .challenge(challenge)
                    .build());
            challengeRoomUserRepository.saveAll(List.of(
                    ChallengeRoomUser.builder()
                            .challengeRoom(challengeRoom)
                            .user(userOne)
                            .build(),
                    ChallengeRoomUser.builder()
                            .challengeRoom(challengeRoom)
                            .user(userTwo)
                            .build()
            ));

            userIds.add(userIdOne);
            userIds.add(userIdTwo);
            challengeRoomId = challengeRoom.getId();
        } catch (InterruptedException ignored) {
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }

        applicationEventPublisher.publishEvent(CompleteMatchingEvent.builder()
                .userIds(userIds)
                .challengeId(event.challengeId())
                .roomId(challengeRoomId)
                .build());
    }
}
