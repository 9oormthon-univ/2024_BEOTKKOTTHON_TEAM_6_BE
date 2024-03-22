package org.goormthon.beotkkotthon.rebook.service.quizhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.QuizHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizHistoryRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quizhistory.ReadQuizHistoryUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadQuizHistoryService implements ReadQuizHistoryUseCase {

    private final UserRepository userRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    @Override
    public QuizHistoryDto execute(UUID userId, Integer quizHistoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        QuizHistory quizHistory = quizHistoryRepository.findById(quizHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (!quizHistory.getUser().getId().equals(user.getId())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return QuizHistoryDto.builder()
                .id(quizHistory.getId())
                .category(quizHistory.getQuiz().getRecycleCategory().getName())
                .title(quizHistory.getQuiz().getTitle())
                .content(quizHistory.getQuiz().getContent())
                .userAnswer(quizHistory.getUserAnswer())
                .validAnswer(quizHistory.getQuiz().getAnswer())
                .build();
    }
}
