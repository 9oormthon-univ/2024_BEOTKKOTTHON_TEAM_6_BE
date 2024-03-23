package org.goormthon.beotkkotthon.rebook.service.quizhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.QuizHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.quizhistory.QuizHistoryListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizHistoryRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quizhistory.ReadQuizHistoryListUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadQuizHistoryListService implements ReadQuizHistoryListUseCase {

    private final UserRepository userRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    @Override
    public List<QuizHistoryListDto> execute(UUID userId, LocalDate whichDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<QuizHistory> quizHistories = quizHistoryRepository.findByUserAndDate(user, whichDate);

        return quizHistories.stream()
                .map(quizHistory -> QuizHistoryListDto.builder()
                        .id(quizHistory.getId())
                        .category(quizHistory.getQuiz().getRecycleCategory().getName())
                        .userAnswer(quizHistory.getUserAnswer())
                        .validAnswer(quizHistory.getQuiz().getAnswer()).build())
                .toList();
    }
}
