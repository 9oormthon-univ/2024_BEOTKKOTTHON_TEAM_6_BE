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
@Table(name = "challenges")
public class Challenge {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_tutorial", nullable = false, updatable = false)
    private Boolean isTutorial;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChallengeCategory> challengeCategories = new ArrayList<>();

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChallengeRoom> challengeRooms = new ArrayList<>();

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public Challenge(
            String description,
            Boolean isTutorial
    ) {
        // Information Column
        this.description = description;
        this.isTutorial = isTutorial;
    }
}
