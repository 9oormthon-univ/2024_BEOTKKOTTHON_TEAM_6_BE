package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingMessageDto;
import org.goormthon.beotkkotthon.rebook.dto.type.EMessage;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
@Hidden
public class TempController {
    private final RabbitTemplate rabbitTemplate;
    private final RedissonClient redissonClient;

    @GetMapping("/send")
    public ResponseDto<?> sendMatchingMessage(
            @RequestParam("challengeId") String challengeId
    ) {
        String routingKey = String.format("challenge.%s.wait.room", challengeId);

        rabbitTemplate.convertAndSend(
                Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                routingKey,
                MatchingMessageDto.builder()
                        .messageType(EMessage.COMPLETE_MATCHING)
                        .receiverId("e6a8dfb8-fdb9-47f8-8a9b-b742d7af6d31").build()
        );

        rabbitTemplate.convertAndSend(
                Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                routingKey,
                MatchingMessageDto.builder()
                        .messageType(EMessage.COMPLETE_MATCHING)
                        .receiverId("096da91d-49ab-4c6f-a649-0b6cbf0a97a3").build()
        );

        return ResponseDto.ok(null);
    }

    @PostMapping("/redis")
    public ResponseDto<?> addRedis(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "value", required = false) String value
    ) {
        String resource = "resource_key";
        int lockTimeout = 2000; // Lock 유지 시간 (밀리초)
        int waitTimeout = 10000; // Lock 획득 대기 시간 (밀리초)

        RLock lock = redissonClient.getLock(resource);

        try {
            boolean isLocked = lock.tryLock(waitTimeout, lockTimeout, TimeUnit.MILLISECONDS);

            redissonClient.getList(key).add(value);
            if (isLocked) {
                // Redlock이 획득되었을 때 수행할 작업을 여기에 추가
                System.out.println("Redlock 획득됨");
                // 작업 수행 후 lock 해제
            } else {
                // Redlock을 획득하지 못했을 때의 처리
                System.out.println("Redlock 획득 실패");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }

        return ResponseDto.ok(null);
    }

    @GetMapping("/redis")
    public ResponseDto<?> readRedis(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "token", required = false) String token
    ) {
        String resource = "resource_key";
        int lockTimeout = 2000; // Lock 유지 시간 (밀리초)
        int waitTimeout = 10000; // Lock 획득 대기 시간 (밀리초)

        RLock lock = redissonClient.getLock(resource);

        List<String> temp = new ArrayList<>();

        try {
            boolean isLocked = lock.tryLock(waitTimeout, lockTimeout, TimeUnit.MILLISECONDS);

            // matching으로시작하는것만 가져오기
            temp.addAll(
                    redissonClient.getList(key).readAll()
                            .stream().map(String::valueOf)
                            .filter(s -> s.startsWith(token)).toList()
            );

            if (isLocked) {
                // Redlock이 획득되었을 때 수행할 작업을 여기에 추가
                System.out.println("Redlock 획득됨");
                // 작업 수행 후 lock 해제
            } else {
                // Redlock을 획득하지 못했을 때의 처리
                System.out.println("Redlock 획득 실패");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }

        return ResponseDto.ok(temp);
    }

    @DeleteMapping("/redis")
    public ResponseDto<?> deleteRedis(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "value", required = false) String value
    ) {
        String resource = "resource_key";
        int lockTimeout = 2000; // Lock 유지 시간 (밀리초)
        int waitTimeout = 10000; // Lock 획득 대기 시간 (밀리초)

        RLock lock = redissonClient.getLock(resource);

        try {
            boolean isLocked = lock.tryLock(waitTimeout, lockTimeout, TimeUnit.MILLISECONDS);

            // value에 해당하는 것만 삭제
            redissonClient.getList(key).remove(value);

            if (isLocked) {
                // Redlock이 획득되었을 때 수행할 작업을 여기에 추가
                System.out.println("Redlock 획득됨");
                // 작업 수행 후 lock 해제
            } else {
                // Redlock을 획득하지 못했을 때의 처리
                System.out.println("Redlock 획득 실패");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }

        return ResponseDto.ok(null);
    }
}
