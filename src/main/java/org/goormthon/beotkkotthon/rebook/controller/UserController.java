package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.response.UserDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingMessageDto;
import org.goormthon.beotkkotthon.rebook.dto.type.EMessage;
import org.goormthon.beotkkotthon.rebook.usecase.user.ReadUserUseCase;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {
    private final ReadUserUseCase readUserUseCase;

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("")
    @Operation(summary = "본인 정보 조회", description = "본인 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "본인 정보 조회 성공",
            content = @Content(schema = @Schema(implementation = UserDetailDto.class)))
    })
    public ResponseDto<UserDetailDto> readUser(
            @Parameter(hidden = true) @UserId UUID userId
    ) {
        return ResponseDto.ok(readUserUseCase.executeMono(userId));
    }

    @GetMapping("/send")
    public ResponseDto<?> sendMatchingMessage(
            @Parameter(hidden = true) @UserId UUID userId
    ) {
        rabbitTemplate.convertAndSend(
                Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                "rooms.1",
                MatchingMessageDto.builder()
                        .messageType(EMessage.COMPLETE)
                        .sender("e6a8dfb8-fdb9-47f8-8a9b-b742d7af6d31")
                        .isSystem(true)
                        .build()
        );

        rabbitTemplate.convertAndSend(
                Constants.CHALLENGE_MATCHING_EXCHANGE_NAME,
                "rooms.1",
                MatchingMessageDto.builder()
                        .messageType(EMessage.COMPLETE)
                        .sender("096da91d-49ab-4c6f-a649-0b6cbf0a97a3")
                        .isSystem(true)
                        .build()
        );

        return ResponseDto.ok(null);
    }
}
