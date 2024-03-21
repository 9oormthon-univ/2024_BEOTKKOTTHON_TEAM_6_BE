package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.annotation.Date;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.request.QuizHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizListDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryListDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryResultDto;
import org.goormthon.beotkkotthon.rebook.usecase.quizhistory.CreateQuizHistoryUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.quizhistory.ReadQuizHistoryListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.quizhistory.ReadQuizHistoryUseCase;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "QuizHistory", description = "퀴즈 기록 관련 API")
public class QuizHistoryController {
    private final CreateQuizHistoryUseCase createQuizHistoryUseCase;
    private final ReadQuizHistoryUseCase readQuizHistoryUseCase;
    private final ReadQuizHistoryListUseCase readQuizHistoryListUseCase;

    @PostMapping("/quiz-histories")
    @Operation(summary = "퀴즈 기록 생성", description = "퀴즈 기록을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀴즈 기록 생성 성공",
                    content = @Content(schema = @Schema(implementation = QuizHistoryResultDto.class)))
    })
    public ResponseDto<?> createQuizHistory(
            @Parameter(hidden = true) @UserId UUID userId,
            @Valid @RequestBody QuizHistoryRequestDto requestDto
    ) {
        return ResponseDto.ok(createQuizHistoryUseCase.execute(userId, requestDto));
    }

    @GetMapping("/quiz-histories/{quizHistoryId}")
    @Operation(summary = "퀴즈 기록 조회", description = "특정 날짜의 퀴즈 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀴즈 기록 조회 성공",
                    content = @Content(schema = @Schema(implementation = QuizHistoryListDto.class)))
    })
    public ResponseDto<?> readQuizHistory(
            @Parameter(hidden = true) @UserId UUID userId,
            @PathVariable("quizHistoryId") Integer quizHistoryId
    ) {
        return ResponseDto.ok(readQuizHistoryUseCase.execute(userId, quizHistoryId));
    }

    @GetMapping("/users/quiz-histories")
    @Operation(summary = "퀴즈 기록 조회", description = "특정 날짜의 퀴즈 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀴즈 기록 조회 성공",
                    content = @Content(schema = @Schema(implementation = QuizHistoryListDto.class)))
    })
    public ResponseDto<?> readQuizHistories(
            @Parameter(hidden = true) @UserId UUID userId,
            @RequestParam("whichDate")
            @Schema(description = "조회할 날짜", example = "2021-08-01") @Date LocalDate whichDate
    ) {
        return ResponseDto.ok(readQuizHistoryListUseCase.execute(userId, whichDate));
    }
}
