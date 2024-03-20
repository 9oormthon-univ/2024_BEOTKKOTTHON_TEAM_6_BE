package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_histories", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_user_quiz",
                columnNames = {"user_id", "quiz_id"})
})
public class QuizHistory {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "user_answer", nullable = false, updatable = false)
    private Boolean userAnswer;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false, updatable = false)
    private Quiz quiz;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public QuizHistory(
            Boolean userAnswer,
            User user,
            Quiz quiz
    ) {
        // Information Column
        this.userAnswer = userAnswer;
        this.createdAt = LocalDateTime.now();

        // Relation Column
        this.user = user;
        this.quiz = quiz;
    }
}
