package org.goormthon.beotkkotthon.rebook.usecase.quizhistory;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryListDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@UseCase
public interface ReadQuizHistoryListUseCase {
    List<QuizHistoryListDto> execute(UUID userId, LocalDate whichDate);
}
