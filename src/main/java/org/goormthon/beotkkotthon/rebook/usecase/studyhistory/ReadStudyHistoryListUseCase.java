package org.goormthon.beotkkotthon.rebook.usecase.studyhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryListDto;

import java.util.List;
import java.util.UUID;

@UseCase
public interface ReadStudyHistoryListUseCase {
    List<StudyHistoryListDto> execute(
            UUID userId,
            Boolean isMarking,
            String category,
            Integer page,
            Integer size
    );
}
