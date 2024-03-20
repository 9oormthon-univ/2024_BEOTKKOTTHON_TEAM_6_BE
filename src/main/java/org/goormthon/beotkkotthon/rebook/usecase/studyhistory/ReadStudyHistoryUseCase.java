package org.goormthon.beotkkotthon.rebook.usecase.studyhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.UserDetailDto;

import java.util.UUID;

@UseCase
public interface ReadStudyHistoryUseCase {
    StudyHistoryDetailDto execute(Integer studyHistoryId);
}
