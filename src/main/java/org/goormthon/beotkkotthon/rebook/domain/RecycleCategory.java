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
@Table(name = "recycle_categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class RecycleCategory {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "recycleCategory", fetch = FetchType.LAZY)
    private List<StudyHistory> studyHistories = new ArrayList<>();

    @OneToMany(mappedBy = "recycleCategory", fetch = FetchType.LAZY)
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "recycleCategory", fetch = FetchType.LAZY)
    private List<ChallengeCategory> challengeCategories = new ArrayList<>();

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public RecycleCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
