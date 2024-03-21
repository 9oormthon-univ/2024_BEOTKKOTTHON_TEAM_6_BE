package org.goormthon.beotkkotthon.rebook.usecase.quizhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.request.QuizHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryResultDto;

import java.util.UUID;

@UseCase
public interface CreateQuizHistoryUseCase {
    QuizHistoryResultDto execute(UUID userId, QuizHistoryRequestDto requestDto);
}
