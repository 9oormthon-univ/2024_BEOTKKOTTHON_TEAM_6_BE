package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.domain.QuizHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizHistoryRepository extends JpaRepository<QuizHistory, Integer> {

    @EntityGraph(attributePaths = {"user", "quiz", "quiz.recycleCategory"})
    Optional<QuizHistory> findById(Integer id);

    Optional<QuizHistory> findByUserAndQuiz(User user, Quiz quiz);

    @Query("SELECT qh FROM QuizHistory qh WHERE qh.user = :user AND DATE(qh.createdAt) = :whichDate")
    List<QuizHistory> findByUserAndDate(
            @Param("user") User user,
            @Param("whichDate") LocalDate whichDate
    );
}
