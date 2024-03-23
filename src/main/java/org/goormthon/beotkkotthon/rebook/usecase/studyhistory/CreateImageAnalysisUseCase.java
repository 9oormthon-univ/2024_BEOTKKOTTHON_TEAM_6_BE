package org.goormthon.beotkkotthon.rebook.usecase.studyhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.request.QuizHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryResultDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.ImageAnalysisDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface CreateImageAnalysisUseCase {
    ImageAnalysisDto execute(UUID userId, MultipartFile image);
}
