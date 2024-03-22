package org.goormthon.beotkkotthon.rebook.service.quizhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.domain.QuizHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.request.QuizHistoryRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryResultDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizHistoryRepository;
import org.goormthon.beotkkotthon.rebook.repository.QuizRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quizhistory.CreateQuizHistoryUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateQuizHistoryService implements CreateQuizHistoryUseCase {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    @Override
    public QuizHistoryResultDto execute(UUID userId, QuizHistoryRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Quiz quiz = quizRepository.findById(requestDto.quizId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        quizHistoryRepository.findByUserAndQuiz(user, quiz)
                .ifPresent(quizHistory -> {
                    throw new CommonException(ErrorCode.DUPLICATED_RESOURCE);
                });

        QuizHistory quizHistory = quizHistoryRepository.save(
                QuizHistory.builder()
                        .user(user)
                        .quiz(quiz)
                        .userAnswer(requestDto.answer())
                        .build()
        );

        return QuizHistoryResultDto.builder()
                .result(quiz.getAnswer().equals(requestDto.answer()))
                .build();
    }
}
