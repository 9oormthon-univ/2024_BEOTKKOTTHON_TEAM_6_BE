package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.response.challengeroom.ChallengeRoomUserDto;
import org.goormthon.beotkkotthon.rebook.usecase.challengeroom.ReadChallengeRoomUserListUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge-rooms")
@Tag(name = "ChallengeRoom", description = "챌린지 룸 관련 API")
public class ChallengeRoomController {
    private final ReadChallengeRoomUserListUseCase readChallengeRoomUserListUseCase;

    @GetMapping("/{challengeRoomId}/users")
    @Operation(summary = "챌린지 룸 사용자 목록 조회", description = "챌린지 룸 사용자 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "챌린지 룸 사용자 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ChallengeRoomUserDto.class)))
    })
    public ResponseDto<?> readChallengeRoomUsers(
            @PathVariable("challengeRoomId") Integer challengeRoomId
    ) {
        return ResponseDto.ok(readChallengeRoomUserListUseCase.execute(challengeRoomId));
    }
}
