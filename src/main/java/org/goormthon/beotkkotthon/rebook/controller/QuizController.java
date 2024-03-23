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
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizListDto;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadRandomQuizListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
@Tag(name = "Quiz", description = "퀴즈 관련 API")
public class QuizController {
    private final ReadRandomQuizListUseCase readRandomQuizListUseCase;
    private final ReadQuizUseCase readQuizUseCase;

    @GetMapping("")
    @Operation(summary = "랜덤 퀴즈 목록 조회", description = "랜덤으로 퀴즈를 3개 선택하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 퀴즈 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = QuizListDto.class)))
    })
    public ResponseDto<List<QuizListDto>> readStudyHistoryList(
            @Parameter(hidden = true) @UserId UUID userId
    ) {
        return ResponseDto.ok(readRandomQuizListUseCase.execute(userId));
    }

    @GetMapping("/{quizId}")
    @Operation(summary = "퀴즈 상세 조회", description = "퀴즈 상세를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공부 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = QuizDetailDto.class)))
    })
    public ResponseDto<QuizDetailDto> readStudyHistory(
            @PathVariable Integer quizId
    ) {
        return ResponseDto.ok(readQuizUseCase.execute(quizId));
    }
}
