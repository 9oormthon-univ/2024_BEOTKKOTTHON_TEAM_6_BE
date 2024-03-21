package org.goormthon.beotkkotthon.rebook.usecase.quizhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryDto;

import java.util.UUID;

@UseCase
public interface ReadQuizHistoryUseCase {
    QuizHistoryDto execute(UUID userId, Integer quizHistoryId);
}
