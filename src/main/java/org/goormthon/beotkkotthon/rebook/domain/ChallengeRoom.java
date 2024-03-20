package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "challenge_rooms")
public class ChallengeRoom {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "challengeRoom", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChallengeRoomUser> challengeRoomUsers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false, updatable = false)
    private Challenge challenge;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public ChallengeRoom(Challenge challenge) {
        // Information Column
        this.createdAt = LocalDateTime.now();

        // Relation Column
        this.challenge = challenge;
    }
}
