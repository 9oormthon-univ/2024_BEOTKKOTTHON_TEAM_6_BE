package org.goormthon.beotkkotthon.rebook.service.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizHistoryRepository;
import org.goormthon.beotkkotthon.rebook.repository.QuizRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService implements ReadQuizListUseCase, ReadQuizUseCase {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    @Override
    public List<QuizListDto> execute(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Integer count = quizHistoryRepository.countByUserAndCreatedAt(user, LocalDate.now());
        log.error("count: {}", count);

        if (count >= 3) {
            return new ArrayList<>();
        }

        List<Quiz> randomQuizList = quizRepository.findAllByUserNotParticipate(
                user,
                PageRequest.of(0, 3 - count)
        );

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
