package org.goormthon.beotkkotthon.rebook.usecase.quiz;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizListDto;

import java.util.List;
import java.util.UUID;

@UseCase
public interface ReadRandomQuizListUseCase {
    List<QuizListDto> execute(UUID userId);
}
