package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationTimeRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.user.UserDto;
import org.goormthon.beotkkotthon.rebook.usecase.user.ReadUserUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationTimeUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationStatusUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {
    private final ReadUserUseCase readUserUseCase;
    private final UpdateUserNotificationStatusUseCase updateUserNotificationStatusUseCase;
    private final UpdateUserNotificationTimeUseCase updateUserNotificationTimeUseCase;

    @GetMapping("")
    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    })
    public ResponseDto<UserDto> readUser(
            @Parameter(hidden = true) @UserId UUID userId
    ) {

        return ResponseDto.ok(readUserUseCase.execute(userId));
    }

    @PatchMapping("/notification")
    @Operation(summary = "알림 설정 수정", description = "알림 설정 여부를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 설정 수정 성공",
                    content = @Content(schema = @Schema(implementation = Object.class)))
    })
    public ResponseDto<Object> updateUserNotification(
            @Parameter(hidden = true) @UserId UUID userId,
            @RequestBody @Valid UserNotificationRequestDto userNotificationRequestDto
    ) {

        return ResponseDto.ok(updateUserNotificationStatusUseCase.execute(userId, userNotificationRequestDto));
    }

    @PatchMapping("/notification-time")
    @Operation(summary = "알림 시간 수정", description = "알림 시간을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 시간 수정 성공",
                    content = @Content(schema = @Schema(implementation = Object.class)))
    })
    public ResponseDto<Object> updateUserNotificationTime(
            @Parameter(hidden = true) @UserId UUID userId,
            @RequestBody @Valid UserNotificationTimeRequestDto userNotificationTimeRequestDto
    ) {

        return ResponseDto.ok(updateUserNotificationTimeUseCase.execute(userId, userNotificationTimeRequestDto));
    }
}
