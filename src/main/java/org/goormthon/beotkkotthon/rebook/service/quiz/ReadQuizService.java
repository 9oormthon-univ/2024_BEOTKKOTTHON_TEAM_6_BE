package org.goormthon.beotkkotthon.rebook.service.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizDetailDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizUseCase;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadQuizService implements ReadQuizUseCase {
    private final QuizRepository quizRepository;

    @Override
    public QuizDetailDto execute(Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return QuizDetailDto.builder()
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .category(quiz.getRecycleCategory().getName()).build();
    }
}
