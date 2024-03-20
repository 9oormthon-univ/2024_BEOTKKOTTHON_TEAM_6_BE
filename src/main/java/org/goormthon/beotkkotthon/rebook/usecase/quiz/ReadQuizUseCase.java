package org.goormthon.beotkkotthon.rebook.usecase.quiz;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.StudyHistoryDetailDto;

@UseCase
public interface ReadQuizUseCase {
    QuizDetailDto execute(Integer quizId);
}
