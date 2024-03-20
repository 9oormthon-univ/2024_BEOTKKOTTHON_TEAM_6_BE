package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.Quiz;
import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query(value = "SELECT * FROM quizzes AS q WHERE q.id NOT IN (SELECT q.id From quizzes AS q INNER JOIN quiz_histories AS qh ON q.id = qh.quiz_id) ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Quiz> findAllByQuizHistories();
}
