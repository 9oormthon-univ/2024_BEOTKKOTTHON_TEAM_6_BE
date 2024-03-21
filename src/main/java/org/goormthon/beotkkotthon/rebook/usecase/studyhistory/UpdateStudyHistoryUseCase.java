package org.goormthon.beotkkotthon.rebook.usecase.studyhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.request.StudyHistoryRequestDto;

@UseCase
public interface UpdateStudyHistoryUseCase {
    Void execute(Integer studyHistoryId, StudyHistoryRequestDto studyHistoryRequestDto);
}
