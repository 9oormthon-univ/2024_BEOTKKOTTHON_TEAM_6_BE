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
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.response.ChallengeDto;
import org.goormthon.beotkkotthon.rebook.dto.response.ChallengeListDto;
import org.goormthon.beotkkotthon.rebook.usecase.challenge.ReadChallengeListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.challenge.ReadChallengeUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Challenge", description = "챌린지 관련 API")
public class ChallengeController {
    private final ReadChallengeListUseCase readChallengeListUseCase;
    private final ReadChallengeUseCase readChallengeUseCase;

    @GetMapping("/users/challenges")
    @Operation(summary = "챌린지 목록 조회", description = "챌린지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "챌린지 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = ChallengeListDto.class)))
    })
    public ResponseDto<?> readChallenges(
            @Parameter(hidden = true)  @UserId UUID userId
    ) {
        return ResponseDto.ok(readChallengeListUseCase.execute(userId));
    }

    @GetMapping("/challenges/{challengeId}")
    @Operation(summary = "챌린지 조회", description = "챌린지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "챌린지 조회 성공",
            content = @Content(schema = @Schema(implementation = ChallengeDto.class)))
    })
    public ResponseDto<?> readChallenge(
            @Parameter(hidden = true) @UserId UUID userId,
            @PathVariable("challengeId") Integer challengeId
    ) {
        return ResponseDto.ok(readChallengeUseCase.execute(userId, challengeId));
    }
}
