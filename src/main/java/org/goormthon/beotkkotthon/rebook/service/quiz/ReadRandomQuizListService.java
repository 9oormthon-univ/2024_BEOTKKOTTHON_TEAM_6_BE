package org.goormthon.beotkkotthon.rebook.service.quiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.domain.QuizHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizDetailDto;
import org.goormthon.beotkkotthon.rebook.dto.response.quiz.QuizListDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.QuizHistoryRepository;
import org.goormthon.beotkkotthon.rebook.repository.QuizRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadRandomQuizListUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.quiz.ReadQuizUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadRandomQuizListService implements ReadRandomQuizListUseCase {
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    @Override
    public List<QuizListDto> execute(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<QuizHistory> todayQuizHistories
                = quizHistoryRepository.findByUserAndCreatedAt(user, LocalDate.now());

        int todayQuizCount = todayQuizHistories.size();

        if (todayQuizCount >= 3) {
            return todayQuizHistories.stream()
                    .map(quizHistory -> QuizListDto.builder()
                            .id(quizHistory.getQuiz().getId())
                            .category(quizHistory.getQuiz().getRecycleCategory().getName())
                            .quizHistoryId(quizHistory.getId()).build())
                    .toList();
        }

        List<Quiz> randomQuizList = quizRepository.findAllByUserNotParticipate(
                user,
                PageRequest.of(0, 3 - todayQuizCount)
        );

        List<QuizListDto> randomQuizzes = new ArrayList<>();

        for (Quiz quiz : randomQuizList) {
            randomQuizzes.add(QuizListDto.builder()
                    .id(quiz.getId())
                    .category(quiz.getRecycleCategory().getName()).build());
        }

        for (QuizHistory quizHistory : todayQuizHistories) {
            randomQuizzes.add(QuizListDto.builder()
                    .id(quizHistory.getQuiz().getId())
                    .category(quizHistory.getQuiz().getRecycleCategory().getName())
                    .quizHistoryId(quizHistory.getId()).build());
        }

        return randomQuizzes;
    }
}
