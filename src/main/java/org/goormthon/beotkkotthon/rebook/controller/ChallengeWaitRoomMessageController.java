package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingMessageDto;
import org.goormthon.beotkkotthon.rebook.dto.type.EMessage;
import org.goormthon.beotkkotthon.rebook.event.CompleteMatchingEvent;
import org.goormthon.beotkkotthon.rebook.usecase.matching.EnterChallengeWaitRoomUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.matching.LeaveChallengeWaitRoomUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@Hidden
public class ChallengeWaitRoomMessageController {
    private final EnterChallengeWaitRoomUseCase enterChallengeWaitRoomUseCase;
    private final LeaveChallengeWaitRoomUseCase leaveChallengeWaitRoomUseCase;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = Constants.CHALLENGE_MATCHING_QUEUE_NAME)
    public void receiveMatchingMessage(MatchingMessageDto matchingMessageDto) {
        log.info("receiveMatchingMessage: {}", matchingMessageDto);
    }

    // 실제 주소 /pub/matching.message/challenge.{challengeId}.wait.room
    // 브로커의 주소 /exchange/matching.exchange/challenge.{challengeId}.wait.room
    @MessageMapping("matching.message/challenge.{challengeId}.wait.room")
    public void sendMatchingMessage(
            StompHeaderAccessor headerAccessor,
            @UserId UUID userId,
            @DestinationVariable("challengeId") Integer challengeId,
            @Valid @Payload MatchingMessageDto requestDto
    ) {
        log.error("sendMatchingMessage: {}", requestDto);
        String routingKey = String.format("challenge.%s.wait.room", challengeId);

        MatchingMessageDto responseDto;

        switch (requestDto.getMessageType()) {
            case ENTER:
                responseDto = enterChallengeWaitRoomUseCase.execute(userId, challengeId);

                Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", userId.toString());
                headerAccessor.getSessionAttributes().put("challengeId", challengeId);
                break;
            case LEAVE:
                leaveChallengeWaitRoomUseCase.execute(userId, challengeId);
                return;
            default:
                responseDto = MatchingMessageDto.builder()
                        .messageType(EMessage.ERROR)
                        .receiverId(userId.toString()).build();
        }

        rabbitTemplate.convertAndSend(
                Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                routingKey,
                Objects.requireNonNull(responseDto)
        );
    }

    @EventListener
    public void completeMatchingListener(CompleteMatchingEvent event) {
        log.info("completeMatchingListener: {}", event);

        List<String> userIds = event.userIds();

        userIds.forEach(userId -> {
            String routingKey = String.format("challenge.%s.wait.room", event.challengeId());
            MatchingMessageDto responseDto = MatchingMessageDto.builder()
                    .messageType(EMessage.COMPLETE_MATCHING)
                    .challengeRoomId(event.roomId())
                    .receiverId(userId).build();

            rabbitTemplate.convertAndSend(
                    Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                    routingKey,
                    Objects.requireNonNull(responseDto)
            );
        });
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        String challengeId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("challengeId");

        leaveChallengeWaitRoomUseCase.execute(UUID.fromString(userId), Integer.valueOf(challengeId));
    }
}
