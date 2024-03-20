package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryListDto;
import org.goormthon.beotkkotthon.rebook.service.studyhistory.StudyHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study-histories")
@Tag(name = "StudyHistory", description = "공부 기록 관련 API")
public class StudyHistoryController {
    private final StudyHistoryService studyHistoryService;

    @GetMapping("")
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

        return ResponseDto.ok(studyHistoryService.readStudyHistoryList(category, page, size));
    }

    @GetMapping("/{studyHistoryId}")
    @Operation(summary = "공부 기록 상세 조회", description = "공부 기록 상세를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공부 기록 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = StudyHistoryDetailDto.class)))
    })
    public ResponseDto<StudyHistoryDetailDto> readStudyHistory(
            @PathVariable Integer studyHistoryId
    ) {

        return ResponseDto.ok(studyHistoryService.readStudyHistory(studyHistoryId));
    }
}
