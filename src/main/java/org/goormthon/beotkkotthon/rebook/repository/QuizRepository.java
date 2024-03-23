package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query(value = "SELECT q FROM Quiz AS q " +
            "WHERE q NOT IN " +
            "(SELECT q From Quiz AS q JOIN q.quizHistories AS qh where qh.user = :user) " +
            "ORDER BY RAND()")
    List<Quiz> findAllByUserNotParticipate(
            @Param("user") User user,
            Pageable pageable
    );
 }
