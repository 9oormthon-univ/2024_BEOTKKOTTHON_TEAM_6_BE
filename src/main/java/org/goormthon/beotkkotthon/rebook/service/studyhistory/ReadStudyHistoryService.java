package org.goormthon.beotkkotthon.rebook.service.studyhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.StudyHistoryRepository;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryUseCase;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReadStudyHistoryService implements ReadStudyHistoryUseCase {
    private final StudyHistoryRepository studyHistoryRepository;

    @Override
    public StudyHistoryDetailDto execute(Integer studyHistoryId) {
        StudyHistory studyHistory = studyHistoryRepository.findById(studyHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        String Date = studyHistory.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return StudyHistoryDetailDto.builder()
                .imageUrl(studyHistory.getImageUrl())
                .content(studyHistory.getRecycleCategory().getDescription())
                .createdAt(Date).build();
    }
}
