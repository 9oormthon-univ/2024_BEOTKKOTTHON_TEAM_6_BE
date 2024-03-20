package org.goormthon.beotkkotthon.rebook.service.studyhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.dto.request.StudyHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryListDto;
import org.goormthon.beotkkotthon.rebook.dto.response.UserDetailDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.StudyHistoryRepository;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.UpdateStudyHistoryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyHistoryService implements ReadStudyHistoryUseCase, ReadStudyHistoryListUseCase, UpdateStudyHistoryUseCase {
    private final StudyHistoryRepository studyHistoryRepository;

    @Override
    public List<StudyHistoryListDto> execute(String category, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudyHistory> studyHistoryList = studyHistoryRepository.findAllByRecycleCategoryName(category, pageable);

        return studyHistoryList.getContent().stream()
                .map(studyHistory -> StudyHistoryListDto.builder()
                        .id(studyHistory.getId())
                        .imageUrl(studyHistory.getImageUrl())
                        .isMarking(studyHistory.getIsMarking())
                        .build())
                .toList();
    }

    @Override
    public StudyHistoryDetailDto executeMono(Integer studyHistoryId) {
        StudyHistory studyHistory = studyHistoryRepository.findById(studyHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return StudyHistoryDetailDto.builder()
                .imageUrl(studyHistory.getImageUrl())
                .content(studyHistory.getRecycleCategory().getDescription())
                .build();
    }

    @Transactional
    @Override
    public Object update(Integer studyHistoryId, StudyHistoryRequestDto studyHistoryRequestDto) {
        StudyHistory studyHistory = studyHistoryRepository.findById(studyHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        studyHistory.updateMarking(studyHistoryRequestDto.isMarking());

        return null;
    }
}
