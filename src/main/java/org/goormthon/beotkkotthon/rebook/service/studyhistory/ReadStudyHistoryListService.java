package org.goormthon.beotkkotthon.rebook.service.studyhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.request.StudyHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.StudyHistoryRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.ReadStudyHistoryUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.UpdateStudyHistoryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadStudyHistoryListService implements ReadStudyHistoryListUseCase {
    private final UserRepository userRepository;
    private final StudyHistoryRepository studyHistoryRepository;

    @Override
    public List<StudyHistoryListDto> execute(UUID userId, Boolean isMarking, String category, Integer page, Integer size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<StudyHistory> studyHistoryList;

        if (isMarking == null) {
            studyHistoryList = studyHistoryRepository.findAllByRecycleCategoryName(user, category, pageable);
        } else {
            studyHistoryList = studyHistoryRepository.findAllByRecycleCategoryNameAndIsMarking(user, category, isMarking, pageable);
        }

        return studyHistoryList.getContent().stream()
                .map(studyHistory -> StudyHistoryListDto.builder()
                        .id(studyHistory.getId())
                        .imageUrl(studyHistory.getImageUrl())
                        .isMarking(studyHistory.getIsMarking())
                        .build())
                .toList();
    }
}
