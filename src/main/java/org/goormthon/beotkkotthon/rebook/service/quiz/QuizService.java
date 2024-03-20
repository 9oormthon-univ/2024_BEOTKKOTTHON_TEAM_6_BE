package org.goormthon.beotkkotthon.rebook.service.quiz;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuizService implements ReadQuizListUseCase, ReadQuizUseCase {
    private final QuizRepository quizRepository;

    @Override
    public List<QuizListDto> execute() {
        List<Quiz> randomQuizList = quizRepository.findAllByQuizHistories();

        return randomQuizList.stream()
                .map(quiz -> QuizListDto.builder()
                    .id(quiz.getId())
                    .category(quiz.getRecycleCategory().getName())
                    .build())
                .toList();
    }

    @Override
    public QuizDetailDto execute(Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return QuizDetailDto.builder()
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .build();
    }
}
