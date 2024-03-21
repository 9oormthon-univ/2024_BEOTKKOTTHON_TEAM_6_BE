package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingMessageDto;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingRoom;
import org.goormthon.beotkkotthon.rebook.dto.type.EMessage;
import org.goormthon.beotkkotthon.rebook.service.challenge.MatchingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@Hidden
public class MatchingMessageController {
    private final RabbitTemplate rabbitTemplate;
    private final MatchingService matchingService;

    // 실제 주소 /pub/challenge.matching.message/rooms.{matchingRoomId}
    @MessageMapping("challenge.matching.message/rooms.{matchingRoomId}")
    public void sendMatchingMessage(
            @UserId UUID userId,
            @DestinationVariable("matchingRoomId") String matchingRoomId,
            @Valid @Payload MatchingMessageDto matchingMessageDto
    ) {
        if (matchingMessageDto.getMessageType() == EMessage.ENTER) {
            log.info("유저 대기실 입장: {}", userId);
            return;
        }

        rabbitTemplate.convertAndSend(
                Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                "rooms." + matchingRoomId,
                matchingMessageDto
        );

        return;
    }

    @RabbitListener(queues = Constants.CHALLENGE_MATCHING_QUEUE_NAME)
    public void receiveMatchingMessage(MatchingMessageDto matchingMessageDto) {
        log.info("receiveMatchingMessage: {}", matchingMessageDto);
    }

    // 연결이 끊어졌을 때
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("SessionDisconnectEvent: {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();
        String matchingRoomId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("matchingRoomId");

        if (matchingRoomId != null) {
            MatchingRoom matchingRoom = matchingService.readMatchingRoom(matchingRoomId);
            matchingRoom.minusUserCount();

//            simpMessageSendingOperations.convertAndSend(
//                    "/sub/matching-rooms/" + matchingRoomId,
//                    MatchingMessageDto.builder()
//                            .messageType(EMessage.FAIL)
//                            .sender(sessionId)
//                            .message(sessionId + "님이 퇴장하셨습니다.")
//                            .build()
//            );
        }
    }
}
