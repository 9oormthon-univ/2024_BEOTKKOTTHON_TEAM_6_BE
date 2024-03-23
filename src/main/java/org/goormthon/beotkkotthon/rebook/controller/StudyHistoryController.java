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
import org.goormthon.beotkkotthon.rebook.dto.request.StudyHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.ImageAnalysisDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryListDto;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.CreateImageAnalysisUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.UpdateStudyHistoryUseCase;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "StudyHistory", description = "공부 기록 관련 API")
public class StudyHistoryController {
    private final ReadStudyHistoryListUseCase readStudyHistoryListUseCase;
    private final ReadStudyHistoryUseCase readStudyHistoryUseCase;
    private final UpdateStudyHistoryUseCase updateStudyHistoryUseCase;
    private final CreateImageAnalysisUseCase createImageAnalysisUseCase;

    @GetMapping("/study-histories")
    @Operation(summary = "공부 기록 목록 조회", description = "공부 기록 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공부 기록 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = StudyHistoryListDto.class)))
    })
    public ResponseDto<List<StudyHistoryListDto>> readStudyHistoryList(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam Integer size
    ) {

        return ResponseDto.ok(readStudyHistoryListUseCase.execute(category, page, size));
    }

    @GetMapping("/study-histories/{studyHistoryId}")
    @Operation(summary = "공부 기록 상세 조회", description = "공부 기록 상세를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공부 기록 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = StudyHistoryDetailDto.class)))
    })
    public ResponseDto<StudyHistoryDetailDto> readStudyHistory(
            @PathVariable Integer studyHistoryId
    ) {

        return ResponseDto.ok(readStudyHistoryUseCase.execute(studyHistoryId));
    }

    @PatchMapping("/study-histories/{studyHistoryId}")
    @Operation(summary = "공부 기록 북마크 상태 변경", description = "공부 기록 북마크 상태를 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공부 기록 북마크 상태 변경 성공",
                    content = @Content(schema = @Schema(implementation = Object.class)))
    })
    public ResponseDto<Object> updateStudyHistory(
            @PathVariable Integer studyHistoryId,
            @RequestBody @Valid StudyHistoryRequestDto studyHistoryRequestDto
    ) {

        return ResponseDto.ok(updateStudyHistoryUseCase.execute(studyHistoryId, studyHistoryRequestDto));
    }

    @PostMapping("/analysis/image")
    @Operation(summary = "사진 분석", description = "사용자가 등록한 사진을 분석합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 분석 성공",
                    content = @Content(schema = @Schema(implementation = ImageAnalysisDto.class)))
    })
    public ResponseDto<ImageAnalysisDto> createImageAnalysis(
            @Parameter(hidden = true) @UserId UUID userId,
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseDto.ok(createImageAnalysisUseCase.execute(userId, image));
    }
}
