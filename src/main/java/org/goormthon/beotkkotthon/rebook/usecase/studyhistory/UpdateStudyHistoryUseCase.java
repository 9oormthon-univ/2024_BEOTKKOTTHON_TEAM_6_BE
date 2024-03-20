package org.goormthon.beotkkotthon.rebook.usecase.studyhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.request.StudyHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.StudyHistoryListDto;

import java.util.List;

@UseCase
public interface UpdateStudyHistoryUseCase {
    Object update(Integer studyHistoryId, StudyHistoryRequestDto studyHistoryRequestDto);
}
