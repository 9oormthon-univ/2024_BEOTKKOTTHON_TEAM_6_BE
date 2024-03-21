package org.goormthon.beotkkotthon.rebook.service.matching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.domain.Challenge;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.repository.ChallengeRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.matching.LeaveChallengeWaitRoomUseCase;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaveChallengeWaitRoomService implements LeaveChallengeWaitRoomUseCase {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    private final RedissonClient redissonClient;

    @Override
    public void execute(UUID userId, Integer challengeId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Challenge> challenge = challengeRepository.findById(challengeId);

        if (user.isEmpty() || challenge.isEmpty()) {
            return;
        }

        String resource = "resource_key";
        String key = Constants.CHALLENGE_KEY + challengeId;
        String value = "matching:" + user.get().getId().toString();

        RLock lock = redissonClient.getLock(resource);

        try {
            boolean isLocked = lock.tryLock(Constants.WAIT_TIMEOUT, Constants.LOCK_TIMEOUT, TimeUnit.MILLISECONDS);

            if (!isLocked) {
                throw new InterruptedException();
            }

            redissonClient.getList(key).remove(value);
            log.info("leaveChallengeWaitRoom: {}", value);
        } catch (InterruptedException e) {
            return;
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
