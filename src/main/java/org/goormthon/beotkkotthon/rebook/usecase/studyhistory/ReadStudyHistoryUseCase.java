package org.goormthon.beotkkotthon.rebook.usecase.studyhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryDetailDto;

@UseCase
public interface ReadStudyHistoryUseCase {
    StudyHistoryDetailDto execute(Integer studyHistoryId);
}
