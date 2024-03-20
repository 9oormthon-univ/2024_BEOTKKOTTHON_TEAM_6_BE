package org.goormthon.beotkkotthon.rebook.service.studyhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.StudyHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyHistoryService {
    private final StudyHistoryRepository studyHistoryRepository;

    public List<StudyHistoryListDto> readStudyHistoryList(String category, Integer page, Integer size) {
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

    public StudyHistoryDetailDto readStudyHistory(Integer studyHistoryId) {
        StudyHistory studyHistory = studyHistoryRepository.findById(studyHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_STUDY_HISTORY));

        return StudyHistoryDetailDto.builder()
                .imageUrl(studyHistory.getImageUrl())
                .content(studyHistory.getRecycleCategory().getDescription())
                .build();
    }
}
