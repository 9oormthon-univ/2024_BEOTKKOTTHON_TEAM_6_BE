package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quizzes")
public class Quiz {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "answer", nullable = false)
    private Boolean answer;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<QuizHistory> quizHistories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "recycle_category_id", nullable = false)
    private RecycleCategory recycleCategory;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public Quiz(
            String title,
            String content,
            Boolean answer,
            RecycleCategory recycleCategory
    ) {
        // Information Column
        this.title = title;
        this.content = content;
        this.answer = answer;

        // Relation Column
        this.recycleCategory = recycleCategory;
    }
}
