package org.goormthon.beotkkotthon.rebook.service.studyhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.dto.request.StudyHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryListDto;
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

@Service
@RequiredArgsConstructor
public class UpdateStudyHistoryService implements UpdateStudyHistoryUseCase {
    private final StudyHistoryRepository studyHistoryRepository;

    @Transactional
    @Override
    public Void execute(Integer studyHistoryId, StudyHistoryRequestDto studyHistoryRequestDto) {
        StudyHistory studyHistory = studyHistoryRepository.findById(studyHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        studyHistory.updateMarking(studyHistoryRequestDto.isMarking());

        return null;
    }
}
